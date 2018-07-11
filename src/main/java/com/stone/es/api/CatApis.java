package com.stone.es.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
/**
 * 获取别名
 * 获取节点占用的空间
 * 获取索引文件数量
 * 获取集群健康状态
 * 获取索引信息，包括文件数量和占用空间
 * 获取全部使用的插件
 * @author zhengchanglin
 *
 */
public class CatApis {

  /**
   * 获取别名
   * @param client
   * @throws IOException
   */
  public static void getAlias(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/aliases?v", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 分配提供了每个数据节点分配了多少个碎片的快照，以及它们使用了多少磁盘空间。
   * @param client
   * @throws IOException
   */
  public static void getAllocation(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/allocation?v", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 索引文件数量。
   * @param client
   * @throws IOException
   */
  public static void getCount(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/count?v", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * fielddata显示了在集群中的每个数据节点上，fielddata目前正在使用多少堆内存。。
   * @param client
   * @throws IOException
   */
  public static void getFielddata(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/fielddata?v", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 集群健康状态
   * @param client
   * @throws IOException
   */
  public static void getHealth(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/health?v", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 获取索引信息，包括文件数量，占用空间大小等
   * @param client
   * @throws IOException
   */
  public static void getIndices(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/indices/*?v&s=index", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 它只显示主节点ID、绑定IP地址和节点名
   * @param client
   * @throws IOException
   */
  public static void getMaster(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/master?v", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * GET /_cat/nodeattrs?v
   * 节点属性
   */
  
  /**
   * GET /_cat/nodes?v
   * 显示集群拓扑
   */
  
  /**
   * GET /_cat/pending_tasks?v
   * 和/_cluster/pending_tasks 相同，格式化
   */
  
  /**
   * 获取在使用的插件
   * @param client
   * @throws IOException
   */
  public static void getPlugins(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cat/plugins?v", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * GET _cat/shards
   * 碎片命令是关于哪些节点包含哪些碎片的详细视图。它会告诉你它是主还是副本，文档的数量，磁盘上的字节，以及它所在的节点。
   */
  
  /**
   * GET /_cat/segments?v
   * 段命令提供关于索引碎片中片段的低级别信息
   */
  
  /**
   * GET /_cat/templates?v
   * 存在的模版
   */
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
