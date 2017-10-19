package com.stone.es;

import java.util.Iterator;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

public class ESClusterClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Client client = ESClient.createClientBySetting();
		Client client = ESClient.createClientShield("elasticsearchXIHU", "admin:000000", "122.112.248.3:9500");
		ClusterAdminClient cac =  client.admin().cluster();
		ClusterStateResponse csr2 = cac.prepareState().get();
//		System.out.println(csr.toString());
//		System.out.println(csr.isContextEmpty());
		MetaData metaData = csr2.getState().getMetaData();
		ImmutableOpenMap<String, IndexMetaData> iom = metaData.getIndices();
		Iterator<ObjectObjectCursor<String, IndexMetaData>> indices = iom.iterator();
		while(indices.hasNext()){
			ObjectObjectCursor<String, IndexMetaData> index = indices.next();
			System.out.println(index.key);
			IndexMetaData imd = index.value;
			Iterator<ObjectObjectCursor<String, MappingMetaData>> ioocm =imd.getMappings().iterator();
			while(ioocm.hasNext()){
				ObjectObjectCursor<String, MappingMetaData> oocm = ioocm.next();
				MappingMetaData mmd = oocm.value.get();
				System.out.println("映射:"+mmd.source().string());
				
			}
		}
		
		
	}

}
