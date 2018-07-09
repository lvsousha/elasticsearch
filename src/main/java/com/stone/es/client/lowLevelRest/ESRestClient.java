package com.stone.es.client.lowLevelRest;


import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

public class ESRestClient {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    RestClient client = ESRestClient.getDefaultRestClientSecurity();
    Response response = client.performRequest("GET", "/");
    RequestLine requestLine = response.getRequestLine();
    HttpHost host = response.getHost();
    int statusCode = response.getStatusLine().getStatusCode();
    Header[] headers = response.getHeaders();
    String responseBody = EntityUtils.toString(response.getEntity());
    System.out.println(requestLine);
    System.out.println("=========");
    System.out.println(host);
    System.out.println("=========");
    System.out.println(statusCode);
    System.out.println("=========");
    System.out.println(headers);
    System.out.println("=========");
    System.out.println(responseBody);
    client.close();
  }

  public static RestClient getDefaultRestClientSecurity() {
    String userName = "elastic";
    String password = "changeme";
    String ip = "122.112.248.222";
    Integer port = 16002;

    return getRestClientSecurity(userName, password, ip, port);
  }

  public static RestClient getRestClientSecurity(String userName, String password, String ip,
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
    RestClient client = builder.build();
    return client;
  }



}
