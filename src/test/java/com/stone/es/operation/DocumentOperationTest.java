package com.stone.es.operation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.RestHighLevelClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stone.es.client.ESRestClient;
import com.stone.es.client.operation.DocumentOperation;
import com.stone.es.model.ESData;
import com.stone.utils.GlobalCase;

public class DocumentOperationTest {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    DocumentOperationTest doc = new DocumentOperationTest();
     doc.indexTest();
//    doc.getTest();
  }

  public void indexTest() throws IOException {
    GlobalCase idWorker = new GlobalCase(0, 0);
    RestHighLevelClient client = ESRestClient.getDefaultHighRestClientSecurity();
    List<ESData> datas = new ArrayList<>();
    DocumentOperation documentOperation = new DocumentOperation();
    for(File file : new File("/ES/20180104json/flfg/flfg").listFiles()){
      JSONObject content = JSON.parseObject(FileUtils.readFileToString(file,"utf-8"));
      JSONObject source = new JSONObject();
      source.put("organization", content.getString("release_dept"));
      source.put("title", content.getString("title"));
      source.put("type", content.getString("type"));
      source.put("content", content.getString("neirong"));
      source.put("createDate", new Date());
      ESData data =
          new ESData("laws", String.valueOf(idWorker.nextId()), source.toJSONString());
      datas.add(data);
//      documentOperation.index(client, data);
      
    }
    documentOperation.bulkIndex(client, datas);
    client.close();
  }

  public void getTest() throws IOException {
    DocumentOperation documentOperation = new DocumentOperation();
    RestHighLevelClient client = ESRestClient.getDefaultHighRestClientSecurity();
    documentOperation.get(client,
        new DocumentOperation.GetModel("stone", "466186875137163264"));
    client.close();
  }



}
