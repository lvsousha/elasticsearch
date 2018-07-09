package com.stone.es;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import com.stone.utils.PropertiesUtils;

public class ESClient {

  public static void main(String[] args) throws Exception {
    // Client client = ESClient.createClientBySetting();
    Client client = ESClient.createClientBySetting();
    // XContentBuilder builder = XContentFactory.jsonBuilder()
    // .startObject()
    // .field("user", "kimchy")
    // .field("postDate", new Date())
    // .field("message", "trying out Elasticsearch")
    // .endObject();
    // IndexResponse response = client.prepareIndex("twitter", "tweet")
    // .setSource(builder.string())
    // .get();
    client.close();

  }

  @SuppressWarnings("resource")
  public static Client createClient() throws Exception {
    Settings settings =
        Settings.builder().put("cluster.name", PropertiesUtils.getString("cluster.name")).build();
    TransportClient client = new PreBuiltTransportClient(settings)
        .addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
        .addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));

    return client;
  }

  @SuppressWarnings("resource")
  public static Client createClientBySetting() throws Exception {
    String clusterName = PropertiesUtils.getString("cluster.name");
    String nodeName = PropertiesUtils.getString("node.name");
    String url = PropertiesUtils.getString("es.url");
    Settings settings =
        Settings.builder().put("cluster.name", clusterName).put("node.name", nodeName)
            // .put("client.transport.sniff", "true")
            // .put("client.transport.ignore_cluster_name", "true") //设置为true可忽略连接的节点的集群名称验证
            // .put("client.transport.ping_timeout", "1000") //等待来自节点的ping响应的时间。 默认为5秒。
            // .put("client.transport.nodes_sampler_interval", "1000") //定时对列出和连接的节点进行抽样/ ping操作。
            // 默认为5秒。
            .build();
    Client client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(
        InetAddress.getByName(url.split(":")[0]), Integer.parseInt(url.split(":")[1])));
    return client;
  }

  @SuppressWarnings("resource")
  public static Client createXpackClient() throws NumberFormatException, UnknownHostException{
    String clusterName = PropertiesUtils.getString("cluster.name");
    String nodeName = PropertiesUtils.getString("node.name");
    String url = PropertiesUtils.getString("es.url");
    Settings settings =
        Settings.builder()
        .put("cluster.name", clusterName)
        .put("node.name", nodeName)
        .put("xpack.security.user","elastic:changeme")
        .build();
    Client client = new PreBuiltXPackTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName("122.112.248.222"), 16002));
    return client;
  }
  // public static Client createClientBySetting(String clusterName, String nodeName, String url)
  // throws Exception{
  // Settings settings = Settings.settingsBuilder()
  // .put("cluster.name", clusterName)
  // .put("node.name", nodeName)
  // .build();
  // Client client = TransportClient.builder()
  // .settings(settings)
  // .build()
  // .addTransportAddress(
  // new InetSocketTransportAddress(
  // InetAddress.getByName(url.split(":")[0]),
  // Integer.parseInt(url.split(":")[1]))
  // );
  // return client;
  // }

  // public static Client createClientShield() throws NumberFormatException, UnknownHostException{
  // String clusterName = PropertiesUtils.getString("cluster.name");
  // String url = PropertiesUtils.getString("es.url");
  // String shieldUser = PropertiesUtils.getString("shield.user");
  // Settings settings = Settings.settingsBuilder()
  // .put("cluster.name", clusterName)
  // .put("shield.user", shieldUser)
  // .build();
  // Client client = TransportClient.builder()
  // .addPlugin(ShieldPlugin.class)
  // .settings(settings)
  // .build()
  // .addTransportAddress(
  // new InetSocketTransportAddress(
  // InetAddress.getByName(url.split(":")[0]),
  // Integer.parseInt(url.split(":")[1]))
  // );
  // return client;
  // }

  // public static Client createClientShield(String clusterName, String shieldUser, String url)
  // throws NumberFormatException, UnknownHostException{
  // Settings settings = Settings.settingsBuilder()
  // .put("cluster.name", clusterName)
  // .put("shield.user", shieldUser)
  // .build();
  // Client client = TransportClient.builder()
  // .addPlugin(ShieldPlugin.class)
  // .settings(settings)
  // .build()
  // .addTransportAddress(
  // new InetSocketTransportAddress(
  // InetAddress.getByName(url.split(":")[0]),
  // Integer.parseInt(url.split(":")[1]))
  // );
  // return client;
  // }
}
