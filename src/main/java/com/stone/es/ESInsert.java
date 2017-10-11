package com.stone.es;

import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import com.alibaba.fastjson.JSONObject;

public class ESInsert {

	private Logger log = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ESInsert esi = new ESInsert();
//		esi.insertSingle(ESClient.createClientBySetting(), "yuhuan", "yanpan");
//		esi.insertBulk(ESClient.createClientBySetting(), "yuhuan", "yanpan");
		esi.insertBulkProcessor(ESClient.createClientBySetting(), "yuhuan", "yanpan");

	}
	
	public IndexResponse insertSingle(Client client, String index, String type){
		JSONObject data = new JSONObject();
		data.put("name", "郑昌林-金");
		data.put("sex", "男");
		data.put("birth", "1993-02-25");
		data.put("year", "1993");
		data.put("createDate", new Date());
		IndexResponse  response  =  client.prepareIndex(index,  type)
                .setSource(data)
                .get();
		log.info(response.isCreated());
		return response;
	}
	
	public BulkResponse insertBulk(Client client, String index, String type){
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		JSONObject data1 = new JSONObject();
		data1.put("name", "郑昌林-火");
		data1.put("sex", "男");
		data1.put("birth", "1993-02-25");
		data1.put("year", "1993");
		data1.put("createDate", new Date());
		JSONObject data2 = new JSONObject();
		data2.put("name", "郑昌林-土");
		data2.put("sex", "男");
		data2.put("birth", "1993-02-25");
		data2.put("year", "1993");
		data2.put("createDate", new Date());
		
		bulkRequest.add(client.prepareIndex(index, type).setSource(data1));
		bulkRequest.add(client.prepareIndex(index, type).setSource(data2));
		
		BulkResponse bulkResponse = bulkRequest.get();
		log.info(bulkResponse.hasFailures());
		
		return bulkResponse;
	}
	
	/**
	 * 如果消息数量到达1000 或者消息大小到大5M 或者时间达到5s 任意条件满足，客户端就会把当前的数据提交到服务端处理
	 * @param client
	 * @param index
	 * @param type
	 */
	public void insertBulkProcessor(Client client, String index, String type){
		BulkProcessor  bulkProcessor  =  BulkProcessor.builder(
                client,   
                new  BulkProcessor.Listener()  {
                        @Override
                        public  void  beforeBulk(long  executionId,BulkRequest  request){
                        	log.info(" 此方法在批量执行之前调用。例如，您可以使用request.numberOfActions()查看numberOfActions");
                        }

                        @Override
                        public  void  afterBulk(long  executionId, BulkRequest  request, BulkResponse  response){
                        	log.info("此方法在批量执行后调用。例如，您可以使用response.hasFailures()检查是否存在一些失败的请求");
                        }

                        @Override
                        public  void  afterBulk(long  executionId, BulkRequest  request, Throwable  failure){
                        	log.info("此方法在批量失败并调用Throwable时调用");
                        }
                })
                .setBulkActions(10000) 			//我们希望每10 000次请求执行批量
                .setBulkSize(new  ByteSizeValue(1,  ByteSizeUnit.GB)) 		//我们想要每1gb批量刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(10)) 			//无论请求数量多少，我们都希望每5秒刷新一次
                .setConcurrentRequests(1) 				//设置并发请求数。值为0表示只允许执行单个请求。值为1表示在累积新的批量请求时允许执行1个并发请求。
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100),  3)) 
                .build();
		for(int i=100; i<11200; i++){
			JSONObject data1 = new JSONObject();
			data1.put("name", String.valueOf(i));
			data1.put("sex", "男");
			data1.put("birth", "1993-02-25");
			data1.put("year", "1993");
			data1.put("createDate", new Date());
			bulkProcessor.add(new  IndexRequest("index", "type", String.valueOf(i)).source(data1));
		}
		bulkProcessor.close();
	}

}
