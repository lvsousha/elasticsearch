package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 准确值
 * @author zhengchanglin
 *
 */
public class KeywordMapping implements Mapping {

  private String type = "keyword";
  private String name;
  private Boolean fields = false;
  private Float boost = 1.0f;               //权重
  private Boolean doc_values = true;        //可以被排序，聚合
  private Boolean index = true;             //是否可以被查询
  private Boolean norms = false;            //评分时，是否考虑字段长度
  private Boolean store = false;            //_source
  

  public KeywordMapping(String name) {
    this.name = name;
  }

  @Override
  public void string(XContentBuilder builder) throws Exception {
    builder.startObject(this.name).field("type", this.type);
    if (this.fields) {
      builder
        .startObject("fields")
          .startObject("raw")
            .field("type", this.type)
            .field("index", "not_analyzed")
            .field("store", "true")
          .endObject()
        .endObject();
    }
    builder.endObject();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Boolean getFields() {
    return fields;
  }

  public void setFields(Boolean fields) {
    this.fields = fields;
  }

  public Float getBoost() {
    return boost;
  }

  public void setBoost(Float boost) {
    this.boost = boost;
  }

  public Boolean getDoc_values() {
    return doc_values;
  }

  public void setDoc_values(Boolean doc_values) {
    this.doc_values = doc_values;
  }

  public Boolean getIndex() {
    return index;
  }

  public void setIndex(Boolean index) {
    this.index = index;
  }

  public Boolean getNorms() {
    return norms;
  }

  public void setNorms(Boolean norms) {
    this.norms = norms;
  }

  public Boolean getStore() {
    return store;
  }

  public void setStore(Boolean store) {
    this.store = store;
  }

}
