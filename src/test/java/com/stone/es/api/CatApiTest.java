package com.stone.es.api;

import java.io.IOException;
import org.elasticsearch.client.RestClient;
import com.stone.es.client.ESRestClient;

public class CatApiTest {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    RestClient client = ESRestClient.getDefaultRestClientSecurity();
    CatApis.getHealth(client);
    CatApis.getAlias(client);
    CatApis.getCount(client);
    CatApis.getIndices(client);
    CatApis.getPlugins(client);
    ClusterApis.getClusterHealth(client);
    client.close();
  }

}
