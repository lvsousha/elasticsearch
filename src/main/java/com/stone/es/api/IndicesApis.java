package com.stone.es.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class IndicesApis {

  
  /**
   * GET /twitter/_mapping/_doc；/_mapping/_doc
   * 获取映射
   * @param client
   * @throws IOException
   */
  public static void getIndicesMappings(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_mapping", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * GET index/_mapping/type/field/a*
   * 获取某个字段的映射
   */
  
  /**
   * POST /_aliases requestBody
   * 创建或者删除别名
   */
  
  /**
   * PUT /index/_settings
   * 实时修改配置
   */
  
  /**
   * 获取配置
   */
  public static void getIndicesSettings(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_all/_settings", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

  /**
   * 获取索引信息
   * @param client
   * @throws IOException
   */
  public static void getIndicesStats(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_stats", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
}
