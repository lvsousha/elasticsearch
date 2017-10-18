package com.stone.es;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.sort.SortParseElement;

import com.stone.es.model.ESData;

public class ESSearch {

	private Logger log = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ESSearch ess = new ESSearch();
//		Client client = ESClient.createClientBySetting();
		Client client = ESClient.createClientShield("elasticsearchXIHU", "admin:000000", "122.112.248.3:9500");
		QueryBuilder query = QueryBuilders.matchAllQuery();
//		QueryBuilder query = QueryBuilders.termQuery("anhao.raw", "最高人民法院");
//		ess.search(client , "flfg", "flfg", query);
//		List<ESData> datas = ess.searchAll(client , "flfg", "flfg", query);
		List<ESData> datas = ess.search(client , "visit", "visit", query);
		System.out.println(datas.size());
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
	
	public List<ESData> search(Client client, String index, String type, QueryBuilder query){
		log.info("START");
		List<ESData> datas = new ArrayList<>();
		SearchResponse  response  =  client.prepareSearch(index)
                .setTypes(type)
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query)                           
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))         
                .setFrom(0).setSize(60)
                .setExplain(true)
                .execute()
                .actionGet();
		log.info("END");
//		log.info(response.getHits().hits().length);
//		log.info(response.toString());
		SearchHits hits = response.getHits();
		for(SearchHit hit : hits.hits()){
			ESData data = new ESData(hit.getIndex(), hit.getType(), hit.getId(), hit.getSourceAsString());
			datas.add(data);
//			log.info(hit.getSourceAsString());
		}
		return datas;
	}

}
