package com.stone.es.client;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESRestClient {

  private static Integer maxRetryTimeoutMillis = 60000;

  public static RestClient getDefaultRestClientSecurity() {
    String userName = "elastic";
    String password = "changeme";
    String ip = "122.112.248.222";
    Integer port = 16001;
    RestClientBuilder builder = getRestClientSecurity(userName, password, ip, port);
    RestClient client = getRestClient(builder);
    return client;
  }

  public static RestHighLevelClient getDefaultHighRestClientSecurity() {
    String userName = "elastic";
    String password = "changeme";
    String ip = "134.175.56.35";
    Integer port = 15000;
    RestClientBuilder builder = getRestClientSecurity(userName, password, ip, port);
    RestHighLevelClient client = getHighRestClient(builder);
    return client;
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
        }).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
          @Override
          public RequestConfig.Builder customizeRequestConfig(
              RequestConfig.Builder requestConfigBuilder) {
            return requestConfigBuilder.setConnectTimeout(maxRetryTimeoutMillis).setSocketTimeout(maxRetryTimeoutMillis);
          }
        });
    builder.setMaxRetryTimeoutMillis(maxRetryTimeoutMillis);
    return builder;
  }

  public static RestClient getRestClient(RestClientBuilder builder) {
    return builder.build();
  }

  public static RestHighLevelClient getHighRestClient(RestClientBuilder builder) {
    return new RestHighLevelClient(builder);
  }



}
