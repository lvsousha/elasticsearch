package com.stone.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public class IpMapping implements Mapping {

  private String type = "ip";
  private String name;

  public IpMapping(String name) {
    this.name = name;
  }

  public IpMapping(String name, String type) {
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
