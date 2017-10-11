package com.stone.es;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;

public class ESSearch {

	private Logger log = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ESSearch ess = new ESSearch();
		ess.search(ESClient.createClientBySetting(), "yuhuan", "yanpan");
	}
	
	public void search(Client client, String index, String... types){
		SearchResponse  response  =  client.prepareSearch(index)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("name",  "郑昌林"))                           
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))         
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
		log.info(response.getHits().hits().length);
	}

}
