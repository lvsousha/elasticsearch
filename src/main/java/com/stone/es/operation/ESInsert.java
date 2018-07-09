package com.stone.es.operation;

import java.util.List;

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

import com.stone.es.model.ESData;

public class ESInsert {

	private Logger log = Logger.getLogger(this.getClass());
	
	private static final int INSERT_SIZE = 5000;
	
	private BulkProcessor bulkProcessor = null;
	
	public IndexResponse insertSingle(Client client, ESData data){
		IndexRequestBuilder build = client.prepareIndex(data.getIndex(),  data.getType());
		if(data.getId() != null  && !data.getId().equals("")){
			build.setId(data.getId());
		}
		IndexResponse  response  =  build.setSource(data.getSource()).get();
		log.info(response.status());
		return response;
	}
	
	/**
	 * 批量插入，一次执行5000条，全部插入为止
	 * @param client
	 * @param datas
	 */
	public void insertBulk(Client client, List<ESData> datas){
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		int num = 0;
		for(int index=0; index<datas.size(); index++){
			num = num + 1;
			ESData data = datas.get(index);
			if(data.getId() != null && !data.getId().equals("")){
				bulkRequest.add(client.prepareIndex(data.getIndex(), data.getType(), data.getId()).setSource(data.getSource()));
			}else{
				bulkRequest.add(client.prepareIndex(data.getIndex(), data.getType()).setSource(data.getSource()));
			}
			if(num < INSERT_SIZE){
				continue;
			}
			BulkResponse bulkResponse = bulkRequest.get();
			log.info(!bulkResponse.hasFailures());
			bulkRequest = client.prepareBulk();
			num = 0;
		}
		if(num > 0){
			BulkResponse bulkResponse = bulkRequest.get();
			log.info(!bulkResponse.hasFailures());
		}
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
