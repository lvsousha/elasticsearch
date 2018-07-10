package com.stone.es.operation;

import java.util.List;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import com.stone.es.mapping.Mapping;
import lombok.Data;

public class IndicesOperation {


  /**
   * 创建索引
   * 
   * @param client RestHighLevelClient
   * @param model 模型
   * @return
   * @throws Exception
   */
  public Boolean createIndex(RestHighLevelClient client, IndicesModel model) throws Exception {
    XContentBuilder builder = XContentFactory.jsonBuilder();
    CreateIndexRequest request = new CreateIndexRequest(model.getIndex());
    request.settings(
        Settings.builder().put("index.number_of_shards", 5).put("index.number_of_replicas", 1));
    decorateMappings(model.getType(), builder, model.getMappings());
    request.mapping(model.getType(), builder);
    if (model.getAlias() != null) {
      request.alias(new Alias(model.getAlias()));
    }
    CreateIndexResponse response = client.indices().create(request);
    return response.isAcknowledged();
  }

  public void decorateMappings(String type, XContentBuilder builder, List<Mapping> mappings)
      throws Exception {
    startMapping(type, builder);
    for (Mapping mapping : mappings) {
      mapping.string(builder);
    }
    endMapping(type, builder);
  }

  private void startMapping(String type, XContentBuilder builder) throws Exception {
    builder.startObject().startObject(type).startObject("properties");
  }

  private void endMapping(String type, XContentBuilder builder) throws Exception {
    builder.endObject().endObject().endObject();
  }

  @Data
  public static class IndicesModel {
    private String index;
    private String type = "doc";
    private String alias;
    private List<Mapping> mappings;

    IndicesModel(String index) {
      this.index = index;
    }

    IndicesModel(String index, String alias) {
      this.index = index;
      this.alias = alias;
    }
  }


}
