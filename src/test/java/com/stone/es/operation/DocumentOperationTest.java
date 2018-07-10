package com.stone.es.operation;

import java.io.IOException;
import org.elasticsearch.client.RestHighLevelClient;
import com.alibaba.fastjson.JSONObject;
import com.stone.es.client.ESRestClient;
import com.stone.es.model.ESData;
import com.stone.es.operation.DocumentOperation;
import com.stone.utils.GlobalCase;

public class DocumentOperationTest {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    DocumentOperationTest doc = new DocumentOperationTest();
    // doc.indexTest();
    doc.getTest();
  }

  public void indexTest() throws IOException {
    GlobalCase idWorker = new GlobalCase(0, 0);
    JSONObject source = new JSONObject();
    source.put("name", idWorker.nextId());
    DocumentOperation documentOperation = new DocumentOperation();
    ESData data =
        new ESData("stone", "stone", String.valueOf(idWorker.nextId()), source.toJSONString());
    RestHighLevelClient client = ESRestClient.getDefaultHighRestClientSecurity();
    documentOperation.index(client, data);
    client.close();
  }

  public void getTest() throws IOException {
    DocumentOperation documentOperation = new DocumentOperation();
    RestHighLevelClient client = ESRestClient.getDefaultHighRestClientSecurity();
    documentOperation.get(client,
        new DocumentOperation.GetModel("stone", "stone", "466186875137163264"));
    client.close();
  }



}
