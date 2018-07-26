package com.stone.es.api.operation;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import com.alibaba.fastjson.JSONObject;
import com.stone.es.client.ESRestClient;

public class AnalyzeOperation {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    System.out.println(Calendar.getInstance().getTimeInMillis());
    RestClient client = ESRestClient.getDefaultRestClientSecurity();
    System.out.println(Calendar.getInstance().getTimeInMillis());
    BasicHeader header = new BasicHeader("Content-type","application/json");
    JSONObject object = new JSONObject();
    Map<String, String> params = new HashMap<>();
    object.put("analyzer", "ik_max_word");
//    object.put("analyzer", "ik_smart");
    object.put("text", "最高人民法院、最高人民检察院关于办理危害生产安全刑事");
    HttpEntity entity = new NStringEntity(object.toJSONString(), "UTF-8");
//    Response response = client.performRequest("GET", "laws/_analyze",params);
    Response response = client.performRequest("POST", "/laws/_analyze?pretty",params, entity, header);
    System.out.println(EntityUtils.toString(response.getEntity()));
    client.close();
  }

}
