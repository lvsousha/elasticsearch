package com.stone.es.mapping;

public class BooleanMapping {

  private String type = "boolean";
  private Double boost = 1.0; // 权重
  private Boolean doc_values = true; // 用于排序和聚合，脚本访问，存储与_source相同的值，但是以排列和聚合方式更有效的以列为导向的方式存储
  private Boolean index = true; // 是否被查询
  private String null_value = "null"; // null_value参数允许您使用指定的值替换显式空值，以便可以对其进行索引和搜索
  private Boolean store = false; // 是否可以被单独检索


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

  public String getNull_value() {
    return null_value;
  }

  public void setNull_value(String null_value) {
    this.null_value = null_value;
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
