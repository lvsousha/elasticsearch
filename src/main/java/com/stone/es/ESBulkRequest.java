package com.stone.es;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class ESBulkRequest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Client client = ESClient.createClientBySetting();
		XContentBuilder  builder  =  XContentFactory.jsonBuilder()
								        .startObject()
								                .field("price",  50)
								                .field("productID",  "DHDK-A-1993-#fJ5")
								        .endObject();
		BulkRequestBuilder brb = client.prepareBulk();
		brb.add(client.prepareIndex("my_store", "products")
						.setId("6")
						.setSource(builder.string())
		);
		BulkResponse br = brb.execute().get();
		
		if(!br.hasFailures()){
			for(BulkItemResponse response : br.getItems()){
				System.out.println(response.isFailed());
				System.out.println(response.getResponse().toString());
			}
		}else{
			System.out.println("失败");
		}
		
		
		
		
		client.close();
	}

}
