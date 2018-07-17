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
  
  public List<Mapping> createCasesIndex(){
    KeywordMapping disputeType = new KeywordMapping("disputeType");
    KeywordMapping court = new KeywordMapping("court");
    KeywordMapping docType = new KeywordMapping("docType");
    KeywordMapping judges = new KeywordMapping("judges");
    KeywordMapping clerk = new KeywordMapping("clerk");
    KeywordMapping appendix = new KeywordMapping("appendix");
    DateMapping refereeDate = new DateMapping("refereeDate");
    TextMapping caseNo = new TextMapping("caseNo");
    TextMapping title = new TextMapping("title");
    TextMapping litigantInfo = new TextMapping("litigantInfo");
    TextMapping caseInfo = new TextMapping("caseInfo");
    TextMapping judgment = new TextMapping("judgment");
    List<Mapping> mappings = new ArrayList<>();
    mappings.add(disputeType);      //  纠纷类型
    mappings.add(court);            //  法院
    mappings.add(docType);          //  文书类型
    mappings.add(judges);           //  审判人员
    mappings.add(clerk);            //  书记员
    mappings.add(appendix);         //  附录
    mappings.add(refereeDate);      //  裁判日期
    mappings.add(caseNo);           //  案件编号
    mappings.add(title);            //  标题
    mappings.add(litigantInfo);     //  当事人信息
    mappings.add(caseInfo);         //  案件信息
    mappings.add(judgment);         //  裁判结果
    return mappings;
  }

}
