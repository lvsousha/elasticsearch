package com.stone.es;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import com.alibaba.fastjson.JSONObject;
import com.stone.es.model.ESData;

public class ESInsert {

	private Logger log = Logger.getLogger(this.getClass());
	
	private BulkProcessor bulkProcessor = null;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Client client = ESClient.createClientBySetting();
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		ESInsert esi = new ESInsert();
		JSONObject object = new JSONObject();
		object.put("user", 2);object.put("keyword", "最高人民法院");object.put("createDate", sdf.format(new Date()));
		ESData data = new ESData("history", "history", object.toString());
		esi.insertSingle(client, data);

	}
	
	public IndexResponse insertSingle(Client client, ESData data){
		IndexRequestBuilder build = client.prepareIndex(data.getIndex(),  data.getType());
		if(data.getId() != null  && !data.getId().equals("")){
			build.setId(data.getId());
		}
		IndexResponse  response  =  build.setSource(data.getSource()).get();
		log.info(response.isCreated());
		return response;
	}
	
	public BulkResponse insertBulk(Client client, List<ESData> datas){
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(int index=0; index<datas.size(); index++){
			ESData data = datas.get(index);
			if(data.getId() != null && !data.getId().equals("")){
				bulkRequest.add(client.prepareIndex(data.getIndex(), data.getType(), data.getId()).setSource(data.getSource()));
			}else{
				bulkRequest.add(client.prepareIndex(data.getIndex(), data.getType()).setSource(data.getSource()));
			}
		}
		BulkResponse bulkResponse = bulkRequest.get();
		log.info(!bulkResponse.hasFailures());
		
		return bulkResponse;
	}
	
	/**
	 * 如果消息数量到达1000 或者消息大小到大5M 或者时间达到5s 任意条件满足，客户端就会把当前的数据提交到服务端处理
	 * @param client
	 * @param index
	 * @param type
	 */
	public void insertBulkProcessor(Client client, List<ESData> datas){
		if(bulkProcessor == null){
			bulkProcessor  =  BulkProcessor.builder(
					client,   
					new BulkProcessor.Listener(){
						@Override
						public  void  beforeBulk(long executionId, BulkRequest request){
							log.info(executionId +" Start Execute "+request.numberOfActions());
						}
						
						@Override
						public  void  afterBulk(long executionId, BulkRequest request, BulkResponse response){
							log.info(executionId+" Execute Is "+!response.hasFailures());
						}
						
						@Override
						public  void  afterBulk(long executionId, BulkRequest request, Throwable failure){
							log.error(executionId, failure);
						}
					})
					.setBulkActions(10000) 			//我们希望每10 000次请求执行批量
					.setBulkSize(new  ByteSizeValue(1,  ByteSizeUnit.GB)) 		//我们想要每1gb批量刷新一次
					.setFlushInterval(TimeValue.timeValueSeconds(10)) 			//无论请求数量多少，我们都希望每10秒刷新一次
					.setConcurrentRequests(1) 				//设置并发请求数。值为0表示只允许执行单个请求。值为1表示在累积新的批量请求时允许执行1个并发请求。
					.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100),  3)) 
					.build();
			
		}
		for(ESData data : datas){
			if(data.getId() != null && !data.getId().equals("")){
				bulkProcessor.add(new  IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getSource()));
			}else{
				bulkProcessor.add(new  IndexRequest(data.getIndex(), data.getType()).source(data.getSource()));
			}
		}
		
	}
	
	public void closeBulkProcessor(){
		bulkProcessor.close();
	}

	public BulkProcessor getBulkProcessor() {
		return bulkProcessor;
	}

	public void setBulkProcessor(BulkProcessor bulkProcessor) {
		this.bulkProcessor = bulkProcessor;
	}
	
	

}
