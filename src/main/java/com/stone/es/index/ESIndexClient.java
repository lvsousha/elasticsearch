package com.stone.es.index;

import java.util.List;

import org.elasticsearch.client.Client;

import com.stone.es.mapping.Mapping;
import com.stone.es.model.ESIndex;
import com.stone.es.model.ESIndexMetadata;
import com.stone.es.model.ESIndexStatus;

public interface ESIndexClient {

	//创建，删除
	public Boolean createIndex(Client client, String index, String type, List<Mapping> mappings) throws Exception;
	public Boolean createAlias(Client client, String index, String alias) throws Exception;
	public Boolean deleteAlias(Client client, String index, String alias) throws Exception;
	public Boolean appendMapping(Client client, String index, String type, List<Mapping> append)  throws Exception;
	/**
	 * 
	 * @param client
	 * @param indices			没有提供索引，不删除，直接返回
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteIndices(Client client, String... indices) throws Exception;
	
	//查询	
	/**
	 * 
	 * @param client
	 * @param indices		没有提供索引，查询全部
	 * @return
	 * @throws Exception
	 */
	public List<ESIndex> getIndices(Client client, String... indices) throws Exception;
	public List<ESIndexStatus> getIndexStatus(Client client, String... indices) throws Exception;
	public List<ESIndexMetadata> getIndexMetadata(Client client, String... indices) throws Exception;
	
}
