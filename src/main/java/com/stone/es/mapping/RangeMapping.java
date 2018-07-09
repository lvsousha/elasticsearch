package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public class RangeMapping implements Mapping {

  private String type; // long,integer,short,byte,double,float
  private String name;
  private Boolean coerce = true; // 是否转换string为number
  private Double boost = 1.0; // 权重
  private Boolean index = true; // 是否可以被查询
  private Boolean store = false;

  public static final String INTEGER_RANGE = "integer_range";
  public static final String FLOAT_RANGE = "float_range";
  public static final String LONG_RANGE = "long_range";
  public static final String DOUBLE_RANGE = "double_range";
  public static final String DATE_RANGE = "date_range";
  public static final String IP_RANGE = "ip_range";

  public RangeMapping(String name) {
    this.name = name;
    this.type = RangeMapping.INTEGER_RANGE;
  }

  public RangeMapping(String name, String type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public void string(XContentBuilder builder) throws Exception {
    builder.startObject(this.name);
    builder.field("type", this.type);
    builder.endObject();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Boolean getCoerce() {
    return coerce;
  }

  public void setCoerce(Boolean coerce) {
    this.coerce = coerce;
  }

  public Double getBoost() {
    return boost;
  }

  public void setBoost(Double boost) {
    this.boost = boost;
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
