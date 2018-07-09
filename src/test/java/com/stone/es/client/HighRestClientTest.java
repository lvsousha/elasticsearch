package com.stone.es.client;

import java.io.IOException;
import org.elasticsearch.client.RestHighLevelClient;

public class HighRestClientTest {
  
  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    RestHighLevelClient client = ESRestClient.getDefaultHighRestClientSecurity();
    client.close();
  }
}
