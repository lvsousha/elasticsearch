package com.stone.es.api;

import java.io.IOException;
import org.elasticsearch.client.RestClient;
import com.stone.es.client.ESRestClient;

public class IndicesApisTest {

  public static void main(String[] args) throws IOException {
    RestClient client = ESRestClient.getDefaultRestClientSecurity();
    System.out.println("------------索引状态------------------");
    IndicesApis.getIndicesStats(client);
    System.out.println("------------索引映射------------------");
    IndicesApis.getIndicesMappings(client);
    System.out.println("------------索引配置------------------");
    IndicesApis.getIndicesSettings(client);
    client.close();
  }
  
}
