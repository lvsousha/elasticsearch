package com.stone.es.client.operation;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import com.stone.es.model.ESData;
import lombok.Data;


/**
 * 索引数据
 * 根据 index,type,id 获取文档
 * 根据 index,type,id 判断文档是否存在
 * 根据 index,type,id 删除文档
 * 批量操作
 *  
 * @author zhengchanglin
 *
 */
public class DocumentOperation {

  private Logger log = Logger.getRootLogger();

  /**
   * 索引数据
   * 
   * @param client
   * @param data
   * @throws IOException
   */
  public void index(RestHighLevelClient client, ESData data) throws IOException {
    IndexRequest request = new IndexRequest(data.getIndex(), data.getType(), data.getId())
        .source(data.getSource(), XContentType.JSON);
    if (data.getType() != null) {
      request.type(data.getType());
    }
    if (data.getId() != null) {
      request.id(data.getId());
    }

    request.opType(DocWriteRequest.OpType.CREATE);
    try {
      IndexResponse response = client.index(request);
      log.info(response.status());
    } catch (ElasticsearchException e) {
      if (e.status() == RestStatus.CONFLICT) {
        log.info("数据已经存在");
      }
    }
  }

  /**
   * 异步索引数据
   * 
   * @param client
   * @param data
   * @throws IOException
   */
  public void indexAsync(RestHighLevelClient client, ESData data) throws IOException {
    IndexRequest request =
        new IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getSource());
    request.opType(DocWriteRequest.OpType.CREATE);
    ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
      @Override
      public void onResponse(IndexResponse response) {
        log.info(response.status());
      }

      @Override
      public void onFailure(Exception e) {
        log.error("索引失败", e);
      }
    };
    client.indexAsync(request, listener);
  }

  /**
   * 根据 index,type,id 获取文档
   * 
   * @param client
   * @param model
   * @throws IOException
   */
  public void get(RestHighLevelClient client, DocumentModel model) throws IOException {
    GetRequest request = new GetRequest(model.getIndex(), model.getType(), model.getId());
    GetResponse response = client.get(request);
    if (response.isExists()) {
      String sourceAsString = response.getSourceAsString();
      log.info(sourceAsString);
    } else {
      log.info("文档不存在");
    }
  }

  /**
   * 根据 index,type,id 判断文档是否存在
   * 
   * @param client
   * @param model
   * @throws IOException
   */
  public Boolean exists(RestHighLevelClient client, DocumentModel model) throws IOException {
    GetRequest request = new GetRequest(model.getIndex(), model.getType(), model.getId());
    return client.exists(request);
  }

  /**
   * 根据 index,type,id 删除文档
   * 
   * @param client
   * @param model
   * @throws IOException
   */
  public Boolean delete(RestHighLevelClient client, DocumentModel model) throws IOException {
    DeleteRequest request = new DeleteRequest(model.getIndex(), model.getType(), model.getId());
    DeleteResponse response = client.delete(request);
    if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
      log.info("文档不存在");
      return false;
    }
    return true;
  }

  public void update() {}

  /**
   * 批量操作
   * @throws IOException 
   */
  public void bulkIndex(RestHighLevelClient client, List<ESData> datas) throws IOException {
    BulkRequest request = new BulkRequest();
    for(ESData data : datas){
      if(request.numberOfActions() < 5000){
        request.add(new IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getSource(), XContentType.JSON));
        continue;
      }
      BulkResponse bulkResponse = client.bulk(request);
      if(bulkResponse.hasFailures()){
        log.info("插入失败");
        return;
      }
      request = new BulkRequest();
    }
    if(request.numberOfActions() > 0){
      BulkResponse bulkResponse = client.bulk(request);
      if(bulkResponse.hasFailures()){
        log.info("插入失败");
        log.info(bulkResponse.buildFailureMessage());
        return;
      }
    }
  }

  public void multiGet() {}

  
  @Data
  public static class DocumentModel {
    private String index;
    private String type = "doc";
    private String id;

    public DocumentModel(String index, String id) {
      this.index = index;
      this.id = id;
    }

    @Deprecated
    public void setType(String type) {
      this.type = type;
    }

  }


}
