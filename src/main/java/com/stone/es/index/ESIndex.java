package com.stone.es.index;

import java.util.List;

import org.elasticsearch.client.Client;

import com.alibaba.fastjson.JSONObject;
import com.stone.es.mapping.Mapping;

public interface ESIndex {

	public Boolean createIndex(Client client, String index, String type, List<Mapping> mappings) throws Exception;
	public Boolean deleteIndices(Client client, String... indices) throws Exception;
	public Boolean createAlias(Client client, String index, String alias) throws Exception;
	public Boolean deleteAlias(Client client, String index, String alias) throws Exception;
	public JSONObject getIndexStatus(Client client, String index) throws Exception;
	public JSONObject getIndexMetadata(Client client, String index) throws Exception;
	
	public Boolean appendMapping(Client client, String index, String type, List<Mapping> append)  throws Exception;
	
	
}
