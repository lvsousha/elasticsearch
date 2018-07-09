package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public class TextMapping implements Mapping {

  private String type = "text";
  private String name;
  private Boolean fields;
  private Boolean fielddata;
  private Boolean index = true;
  private Boolean store = true;

  /**
   * 
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
      builder.startObject("fields").startObject("raw").field("type", "keyword")
          .field("store", "true").endObject().endObject();
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

  public Boolean getIndex() {
    return index;
  }

  public void setIndex(Boolean index) {
    this.index = index;
  }

  public Boolean getStore() {
    return store;
  }

  public void setStore(Boolean store) {
    this.store = store;
  }

  public Boolean getFielddata() {
    return fielddata;
  }

  public void setFielddata(Boolean fielddata) {
    this.fielddata = fielddata;
  }

}
