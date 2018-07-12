package com.stone.es.operation;

import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.client.RestHighLevelClient;
import com.stone.es.client.ESRestClient;
import com.stone.es.client.operation.IndicesOperation;
import com.stone.es.constants.AnalyzersConstants;
import com.stone.es.mapping.DateMapping;
import com.stone.es.mapping.KeywordMapping;
import com.stone.es.mapping.Mapping;
import com.stone.es.mapping.TextMapping;

public class IndicesOperationTest {

  public static void main(String[] args) throws Exception {
    RestHighLevelClient client = ESRestClient.getDefaultHighRestClientSecurity();
    IndicesOperation.IndicesModel model =
        new IndicesOperation.IndicesModel("laws-v0", "laws");
    List<Mapping> mappings = new ArrayList<>();
    TextMapping content = new TextMapping("content",AnalyzersConstants.IK_SMART);
    KeywordMapping title = new KeywordMapping("title");
    KeywordMapping organization = new KeywordMapping("organization");
    KeywordMapping type = new KeywordMapping("type");
    DateMapping createDate = new DateMapping("createDate");
    mappings.add(content);
    mappings.add(title);
    mappings.add(organization);
    mappings.add(type);
    mappings.add(createDate);
    model.setMappings(mappings);
    IndicesOperation io = new IndicesOperation();
    Boolean flag = io.createIndex(client, model);
    System.out.println(flag);
    client.close();
  }

}
