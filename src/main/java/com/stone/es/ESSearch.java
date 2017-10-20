package com.stone.es;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FilteredQueryBuilder;
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
import com.stone.es.model.ESData;

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
		SearchResponse  response  =  client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())           
                .addAggregation(
                		AggregationBuilders.terms("agg1").field("release_date.raw")
                )
                .setFrom(0)
                .setSize(60)
                .setExplain(true)
                .execute()
                .actionGet();
		StringTerms agg1 = response.getAggregations().get("agg1");
		System.out.println(response.toString());
		for(Bucket bucket :agg1.getBuckets()){
			log.info(bucket.getKeyAsString()+"===="+bucket.getDocCount());
		};
	}

}
