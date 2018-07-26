package com.stone.es.api;

import java.io.IOException;
import org.elasticsearch.client.RestClient;
import com.stone.es.client.ESRestClient;

public class CatApiTest {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    RestClient client = ESRestClient.getDefaultRestClientSecurity();
    System.out.println("------------索引健康状态------------------");
    CatApis.getHealth(client);
    System.out.println("------------别名------------------");
    CatApis.getAlias(client);
    System.out.println("------------数量------------------");
    CatApis.getCount(client);
    System.out.println("------------索引状态------------------");
    CatApis.getIndices(client);
    System.out.println("------------插件状态------------------");
    CatApis.getPlugins(client);
    ClusterApis.getClusterHealth(client);
    client.close();
  }

}
