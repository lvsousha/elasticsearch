package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public class DateMapping implements Mapping {

  private String type = "date";
  private String name;
  private Double boost = 1.0; // 权重
  private Boolean doc_values = true;
  private String format = "yyyy-MM-dd HH:mm:ss.SSS||epoch_millis";
  private Boolean ignore_malformed = false; // 是否忽略日期格式错误的文档
  private Boolean include_in_all; // 是否进入_all字段
  private Boolean index = true;
  private String null_value = "null";
  private Integer precision_step = 16;
  private Boolean store = false;

  public DateMapping(String name) {
    this.name = name;
  }

  @Override
  public void string(XContentBuilder builder) throws Exception {
    builder.startObject(this.name);
    builder.field("type", this.type).field("format", this.format);
    builder.endObject();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getBoost() {
    return boost;
  }

  public void setBoost(Double boost) {
    this.boost = boost;
  }

  public Boolean getDoc_values() {
    return doc_values;
  }

  public void setDoc_values(Boolean doc_values) {
    this.doc_values = doc_values;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Boolean getIgnore_malformed() {
    return ignore_malformed;
  }

  public void setIgnore_malformed(Boolean ignore_malformed) {
    this.ignore_malformed = ignore_malformed;
  }

  public Boolean getInclude_in_all() {
    return include_in_all;
  }

  public void setInclude_in_all(Boolean include_in_all) {
    this.include_in_all = include_in_all;
  }


  public String getNull_value() {
    return null_value;
  }

  public void setNull_value(String null_value) {
    this.null_value = null_value;
  }

  public Integer getPrecision_step() {
    return precision_step;
  }

  public void setPrecision_step(Integer precision_step) {
    this.precision_step = precision_step;
  }

  public Boolean getStore() {
    return store;
  }

  public void setStore(Boolean store) {
    this.store = store;
  }

  public Boolean getIndex() {
    return index;
  }

  public void setIndex(Boolean index) {
    this.index = index;
  }

}
