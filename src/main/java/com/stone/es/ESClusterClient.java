package com.stone.es;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.IndexMetaData.Custom;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

public class ESClusterClient {

	private Logger log = Logger.getRootLogger();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ESClusterClient escc = new ESClusterClient();
//		Client client = ESClient.createClientBySetting();
		Client client = ESClient.createClientShield("robot", "admin:000000", "122.112.210.226:9387");
		escc.state(client);
		
		
	}
	
	public void state(Client client) throws Exception{
		ClusterAdminClient cac =  client.admin().cluster();
		ClusterStateResponse csr = cac.prepareState().get();
		log.info("集群名："+csr.getClusterName());
		ClusterState cs = csr.getState();
		log.info(cs.toString());
		log.info(cs.status().name());
		MetaData metaData = cs.getMetaData();
		ImmutableOpenMap<String, IndexMetaData> iom = metaData.getIndices();
		Iterator<ObjectObjectCursor<String, IndexMetaData>> indices = iom.iterator();
		while(indices.hasNext()){
			ObjectObjectCursor<String, IndexMetaData> index = indices.next();
			log.info("索引名："+index.key);
			IndexMetaData imd = index.value;
			getMapping(imd.getMappings().iterator());
			getAliases(imd.getAliases().iterator());
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(imd.getCreationDate());
			log.info("创建时间："+calendar.getTime());
			log.info("ES版本："+imd.getCreationVersion());
			getCustoms(imd.getCustoms().iterator());
			log.info("复制分片数量"+imd.getNumberOfReplicas());
			log.info("主分片数量："+imd.getNumberOfShards());
			log.info(imd.getState().name());
			getSettings(imd.getSettings().getAsMap());
		}
		
	}
	
	public void getMapping(Iterator<ObjectObjectCursor<String, MappingMetaData>> ioocm) throws Exception{
		while(ioocm.hasNext()){
			ObjectObjectCursor<String, MappingMetaData> oocm = ioocm.next();
			MappingMetaData mmd = oocm.value.get();
			log.info("映射:"+mmd.source().string());
		}
	}
	
	public void getAliases(Iterator<ObjectObjectCursor<String, AliasMetaData>> iooca){
		while(iooca.hasNext()){
			ObjectObjectCursor<String, AliasMetaData> ooca = iooca.next();
			log.info(ooca.key);
			AliasMetaData amd = ooca.value.get();
			log.info("别名："+amd.toString());
			
		}
	}

	public void getCustoms(Iterator<ObjectObjectCursor<String, Custom>> ioocc){
		while(ioocc.hasNext()){
			ObjectObjectCursor<String, Custom> oocc = ioocc.next();
			log.info(oocc.key);
			log.info(oocc.value.toString());
		}
	}
	
	public void getSettings(Map<String,String> map){
		for(String key : map.keySet()){
			log.info(key+":"+map.get(key));
		}
	}
}
