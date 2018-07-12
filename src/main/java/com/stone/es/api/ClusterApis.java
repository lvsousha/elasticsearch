package com.stone.es.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class ClusterApis {

  /**
   * 获取集群健康状态
   * @param client
   * @throws IOException
   */
  public static void getClusterHealth(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    params.put("timeout", "50s");
    Response response = client.performRequest("GET", "/_cluster/health?pretty", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 集群状态API允许获得整个集群的全面状态信息。
   * @param client
   * @throws IOException
   */
  public static void getClusterState(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cluster/state", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 集群Stats API允许从集群的角度检索统计信息。这个API返回基本的索引指标（碎片编号、存储大小、内存使用量）和关于组成集群的当前节点的信息（数字、角色、操作系统、jvm版本、内存使用、cpu和已安装的插件）
   * @param client
   * @throws IOException
   */
  public static void getClusterStats(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cluster/stats?human&pretty", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 尚未执行的任务
   * @param client
   * @throws IOException
   */
  public static void getPendingClusterTasks(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cluster/pending_tasks", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * POST /_cluster/reroute
   * 重新路由命令允许手动更改集群中的单个碎片的分配。例如，一个碎片可以被显式地从一个节点移动到另一个节点，一个分配可以被取消，一个未分配的碎片可以显式地分配给一个特定的节点。
   */
  
  /**
   * PUT /_cluster/settings
   * 允许更新集群范围的特定设置。更新的设置可以是持久性的（跨重启应用）或瞬态（在整个集群重新启动时无法存活）。
   * @throws IOException 
   */
  
  
  public static void getNodesStats(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_nodes/stats", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  public static void getNodes(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_nodes", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  public static void getNodesUsage(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_nodes/usage", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 当前执行的任务
   * @param client
   * @throws IOException
   */
  public static void getTasks(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_tasks", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

  /**
   * 获取每个节点的最热线程
   * @param client
   * @throws IOException
   */
  public static void getNodesHotThreads(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_nodes/hot_threads", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
  /**
   * 集群分配解释API的目的是为集群中的碎片分配提供解释。
   * 对于未分配的碎片，explain API提供了一个解释为什么碎片没有被分配。对于分配的碎片，explain API提供了一个解释，解释为什么碎片会保留在当前节点上，并且没有移动或重新平衡到另一个节点。当您试图诊断为什么碎片没有被分配，或者为什么碎片在当前节点上继续保留时，这个API是非常有用的。
   */
  public static void getClusterAllocationExplain(RestClient client) throws IOException{
    Map<String,String> params = new HashMap<>();
    Response response = client.performRequest("GET", "/_cluster/allocation/explain", params);
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
  
}
