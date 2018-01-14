package com.stone.es;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.sort.SortParseElement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.es.http.ESHttp;
import com.stone.es.model.ESData;
import com.stone.es.operation.ESInsert;

public class ESSearch {

	private Logger log = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ESSearch ess = new ESSearch();
		Client client = ESClient.createClientBySetting();
//		QueryBuilder query = QueryBuilders.matchAllQuery();
		QueryBuilder query = QueryBuilders.termQuery("anhao.raw", "最高人民法院");
//		ess.search(client , "flfg", "flfg", query);
//		List<ESData> datas = ess.searchAll(client , "flfg", "flfg", query);
//		List<ESData> datas = ess.search(client , "flfg", "flfg", query, 0, 10);
//		System.out.println(datas.size());
		ess.search(client , "flfg", "flfg");
		client.close();
	}

	public List<ESData> searchAll(Client client, String index, String type, QueryBuilder query){
		log.info("Select "+index+"-"+type+" Start");
		List<ESData> datas = new ArrayList<>();
		SearchResponse scrollResp = client.prepareSearch(index)
				.setTypes(type)
		        .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
		        .setScroll(new TimeValue(60000))
		        .setQuery(query)
		        .setSize(5000)
		        .execute()
		        .actionGet();
		while(true){
			for(SearchHit hit : scrollResp.getHits().getHits()) {
		    	ESData data = new ESData(hit.getIndex(), hit.getType(), hit.getId(), hit.getSourceAsString());
				datas.add(data);
		    }
		    scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
		    					.setScroll(new TimeValue(60000))
		    					.execute()
		    					.actionGet();
		    if(scrollResp.getHits().getHits().length == 0){
		        break;
		    }
		}
		log.info("Select "+index+"-"+type+" End");
		return datas;
	}
	
	public List<ESData> search(Client client, String index, String type, QueryBuilder query, int from, int size){
		log.info("START");
		List<ESData> datas = new ArrayList<>();
		SearchResponse  response  =  client.prepareSearch(index)
                .setTypes(type)
                .setQuery(query)                           
                .setFrom(from)
                .setSize(size)
                .setExplain(true)
                .execute()
                .actionGet();
		log.info("END");
		SearchHits hits = response.getHits();
		for(SearchHit hit : hits.hits()){
			ESData data = new ESData(hit.getIndex(), hit.getType(), hit.getId(), hit.getSourceAsString());
			datas.add(data);
			log.info(hit.getSourceAsString());
		}
		return datas;
	}
	
	
	public void search(Client client, String index, String type){
		ESInsert esi = new ESInsert();
		JSONObject object = new JSONObject();
		object.put("user", 1);object.put("keyword", "最高人民法院");object.put("createDate", new Date());
		ESData data = new ESData("history", "history", object.toString());
		esi.insertSingle(client, data);
		QueryBuilder qb = null;
		QueryBuilder term = QueryBuilders.termQuery("title.raw", "最高人民法院");
		QueryBuilder bool = QueryBuilders.boolQuery()
							.must(QueryBuilders.termQuery("type.raw", "司法解释"))
							.mustNot(QueryBuilders.termQuery("title.raw", "最高人民法院"))
							.should(QueryBuilders.termQuery("release_date.raw", "2015-06-29"))
							.should(QueryBuilders.termQuery("release_date.raw", "2015-07-20"))
							;
		QueryBuilder match = QueryBuilders.matchQuery("neirong", "中华人民共和国")	//默认是or
//							.operator(Operator.AND)				//转化为and
//							.minimumShouldMatch("50%")			//最少匹配数量；必须匹配的词项数用来表示一个文档是否相关
//							.boost(2f)							//提升权重
				;
		QueryBuilder mulitMatch = QueryBuilders.multiMatchQuery("2015-06-29")
												.type(Type.BEST_FIELDS)			//默认类型			cross_fields 词中心式匹配
												.field("effective_date", 2f)
												.field("release_date")
												;
		//best_fields——将任何与任一查询匹配的文档作为结果返回，但只将最佳匹配的评分作为查询的评分结果返回    试用于should   
		qb = QueryBuilders.disMaxQuery()
							.add(bool)
							.tieBreaker(0.3f)		//tie_breaker 可以是 0 到 1 之间的浮点数，其中 0 代表使用 dis_max 最佳匹配语句的普通逻辑， 1 表示所有匹配语句同等重要
							;
		//constant_score 查询以非评分模式来执行 term 查询并以1.0作为统一评分,    也就是将query转换为filter
		QueryBuilder constantScore = QueryBuilders.constantScoreQuery(qb);
		
		QueryBuilder matchPhraseQuery = QueryBuilders.matchPhraseQuery("neirong", "台湾地区")	// match_phrase 查询首先将查询字符串解析成一个词项列表，然后对这些词项进行搜索，但只保留那些包含 全部 搜索词项，且 位置 与搜索词项相同的文档
														.slop(2)							//slop 参数告诉 match_phrase 查询词条相隔多远时仍然能将文档视为匹配
														;
		
		SearchRequestBuilder srr = client.prepareSearch(index)
							                .setTypes(type)
							                .setQuery(matchPhraseQuery) 
							                .addAggregation(
							                		AggregationBuilders.terms("agg1")
//							                								.field("title.raw")
							                								.field("effective_date.raw")
//							                							.size(100)
							                )
							                .addHighlightedField("neirong")
							                .setHighlighterPreTags("<EM>").setHighlighterPostTags("</EM>")
							                .setFrom(0)
							                .setSize(5)
							                ;
		
		
		SearchResponse response = srr.execute().actionGet();
		StringTerms agg1 = response.getAggregations().get("agg1");
		for(Bucket bucket :agg1.getBuckets()){
			log.info(bucket.getKeyAsString()+"===="+bucket.getDocCount());
		}
//		log.info(constantScore.toString());
		log.info(srr.toString());
		ESHttp.explain(client.prepareSearch(index).setTypes(type).setQuery(qb).toString());
//		log.info(response.getHits().getTotalHits());
		log.info(response.toString());
		log.info(agg1.getBuckets().size());
	}

}
