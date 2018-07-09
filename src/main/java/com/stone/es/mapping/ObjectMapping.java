package com.stone.es.mapping;

import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class ObjectMapping implements Mapping {

  private String name;
  private String type = "object";
  private Boolean dynamic = true;
  private Boolean enabled = true; // 是否索引json数据
  private List<Mapping> properties = new ArrayList<>();

  @Override
  public void string(XContentBuilder builder) throws Exception {
    builder.startObject(this.name).field("type", this.type);
    builder.endObject();
  }
  
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Boolean getDynamic() {
    return dynamic;
  }

  public void setDynamic(Boolean dynamic) {
    this.dynamic = dynamic;
  }

  public List<Mapping> getProperties() {
    return properties;
  }

  public void setProperties(List<Mapping> properties) {
    this.properties = properties;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }


}
