package com.stone.es.api;

import java.io.IOException;
import org.elasticsearch.client.RestClient;
import com.stone.es.client.ESRestClient;

public class ClusterApisTest {

  public static void main(String[] args) throws IOException {
    RestClient client = ESRestClient.getDefaultRestClientSecurity();
    System.out.println("------------集群健康状态------------------");
    ClusterApis.getClusterHealth(client);
    System.out.println("------------集群状态------------------");
    ClusterApis.getClusterState(client);
    client.close();
  }
}
