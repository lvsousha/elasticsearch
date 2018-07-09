package com.stone.es.operation;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
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
    IndexRequest request =
        new IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getSource());
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


}
