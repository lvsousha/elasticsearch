package com.stone.es.client;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class RestClientTest {

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
  
}
