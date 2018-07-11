package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;
import com.stone.es.constants.AnalyzersConstants;
import lombok.Data;

@Data
public class TextMapping implements Mapping {

  private String type = "text";
  private String name;
  private Boolean fields = false;
  private Boolean fielddata = false;
  private Boolean index = true;
  private Boolean store = true;
  private String analyzer = AnalyzersConstants.IK_MAX_WORD;

  public TextMapping(String name) {
    this.name = name;
  }
  
  public TextMapping(String name, String analyzer) {
    this.name = name;
    this.analyzer = analyzer;
  }
  
  /**
   * 是否需要一个不分词的字段
   * @param name
   * @param fields          内容少用false，内容多用true
   */
  public TextMapping(String name, Boolean fields) {
    this.name = name;
    this.fields = fields;
    this.fielddata = !fields;
  }

  @Override
  public void string(XContentBuilder builder) throws Exception {
    builder.startObject(this.name).field("type", this.type);
    if (this.fielddata) {
      builder.field("fielddata", this.fielddata);
    } else if (this.fields) {
      builder.startObject("fields")
              .startObject("raw")
                .field("type", "keyword")
                .field("store", "true")
              .endObject()
             .endObject();
    }
    builder.field("analyzer", this.analyzer);
    builder.endObject();
  }

}
