package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public class GeoShapeMapping implements Mapping {

  private String type = "geo_shape";
  private String name;

  public GeoShapeMapping(String name) {
    this.name = name;
  }

  public GeoShapeMapping(String name, String type) {
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

}