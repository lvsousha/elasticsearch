package com.stone.es.client;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESRestClient {

  public static RestClient getDefaultRestClientSecurity() {
    String userName = "elastic";
    String password = "changeme";
    String ip = "122.112.248.222";
    Integer port = 16002;
    RestClientBuilder builder = getRestClientSecurity(userName, password, ip, port);
    return getRestClient(builder);
  }

  public static RestHighLevelClient getDefaultHighRestClientSecurity() {
    String userName = "elastic";
    String password = "changeme";
    String ip = "122.112.248.222";
    Integer port = 16002;
    RestClientBuilder builder = getRestClientSecurity(userName, password, ip, port);
    return getHighRestClient(builder);
  }

  public static RestClientBuilder getRestClientSecurity(String userName, String password, String ip,
      Integer port) {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY,
        new UsernamePasswordCredentials(userName, password));
    RestClientBuilder builder = RestClient.builder(new HttpHost(ip, port))
        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
          @Override
          public HttpAsyncClientBuilder customizeHttpClient(
              HttpAsyncClientBuilder httpClientBuilder) {
            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
          }
        });
    return builder;
  }

  public static RestClient getRestClient(RestClientBuilder builder) {
    return builder.build();
  }

  public static RestHighLevelClient getHighRestClient(RestClientBuilder builder) {
    return new RestHighLevelClient(builder);
  }



}
