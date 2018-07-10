package com.stone.es.operation;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import com.stone.es.model.ESData;

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
  public void get(RestHighLevelClient client, GetModel model) throws IOException {
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
  public Boolean exists(RestHighLevelClient client, GetModel model) throws IOException {
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
  public Boolean delete(RestHighLevelClient client, GetModel model) throws IOException {
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
   */
  public void bulk() {}

  public void multiGet() {}

  public static class GetModel {
    private String index;
    private String type;
    private String id;

    GetModel(String index, String type, String id) {
      this.setIndex(index);
      this.setType(type);
      this.setId(id);
    }

    public String getIndex() {
      return index;
    }

    public void setIndex(String index) {
      this.index = index;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }
  }


}
