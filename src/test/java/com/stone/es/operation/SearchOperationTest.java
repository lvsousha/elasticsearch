package com.stone.es.operation;

import java.io.IOException;
import org.elasticsearch.client.RestHighLevelClient;
import com.stone.es.client.ESRestClient;

public class SearchOperationTest {

  public static void main(String[] args) throws IOException {
    RestHighLevelClient client = ESRestClient.getDefaultHighRestClientSecurity();
    SearchOperation so = new SearchOperation();
    
    so.search(client);
    client.close();
  }
  
}
