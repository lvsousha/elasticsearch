package com.stone.es;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.stone.es.client.ESRestClient;
import com.stone.es.client.operation.DocumentOperation;
import com.stone.es.model.ESData;
import com.stone.utils.GlobalCase;

public class InsertData {

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
  
  public static void main(String[] args) throws Exception {
    // TODO Auto-generated method stub
    InsertData id = new InsertData();
    String fold = args[0];
//    String fold = "/JSON/case/example";
    RestHighLevelClient client = id.getClient();
    try{
      for (File dir : new File(fold).listFiles()) {
        id.insert(client, dir);
      }
    } finally {
      client.close();
    }
  }

  public void insert(RestHighLevelClient client, File dir) throws Exception {
    Date createDate = new Date();
    GlobalCase idWorker = new GlobalCase(0, 0);
    List<ESData> datas = new ArrayList<>();
    DocumentOperation documentOperation = new DocumentOperation();
    for (File file : dir.listFiles()) {
      String contents = "";
      for (String s : Files.readLines(file, Charsets.UTF_8)) {
        contents += s;
      }
      JSONObject content = JSON.parseObject(contents.replaceAll("\\\\n", "<br>"));
      JSONObject source = new JSONObject();
      source.put("disputeType", content.getString("纠纷类型"));
      source.put("title", content.getString("标题"));
      source.put("court", content.getString("法院"));
      source.put("docType", content.getString("文书类型"));
      source.put("caseNo", content.getString("案号"));
      source.put("litigantInfo", content.getString("当事人信息"));
      source.put("caseInfo", content.getString("案件信息"));
      source.put("judgment", content.getString("裁判结果"));
      source.put("judges", content.getString("审判人员"));
      source.put("refereeDate", content.getString("裁判日期"));
      source.put("clerk", content.getString("书记员"));
      source.put("appendix", content.getString("附录"));
      source.put("createDate", sdf.format(createDate));
      ESData data = new ESData("cases", String.valueOf(idWorker.nextId()), source.toJSONString());
      datas.add(data);
      if (datas.size() < 5000) {
        continue;
      }
      System.out.println("5000");
      documentOperation.bulkIndex(client, datas);
      datas.clear();
    }
    if (datas.size() > 0) {
      documentOperation.bulkIndex(client, datas);
    }

  }

  public RestHighLevelClient getClient() {
    RestClientBuilder builder =
        ESRestClient.getRestClientSecurity("elastic", "changeme", "172.16.0.9", 15000);
    return new RestHighLevelClient(builder);
  }

}
