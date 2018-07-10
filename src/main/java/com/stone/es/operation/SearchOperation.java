package com.stone.es.operation;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchOperation {

  private Logger log = Logger.getRootLogger();
  
  public void search(RestHighLevelClient client) throws IOException{
    log.info("START");
    SearchRequest request = new SearchRequest(); 
    request.indices("laws");
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
    sourceBuilder.query(QueryBuilders.matchPhraseQuery("content", "最高人民法院、最高人民检察院关于办理危害生产安全刑事")); 
    sourceBuilder.from(0);
    sourceBuilder.size(5);
    request.source(sourceBuilder);
    SearchResponse response = client.search(request);
    log.info(response.getHits().totalHits);
  }
  
  
}
