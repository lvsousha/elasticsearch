package com.stone.es.http;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtil {
	
	private static Logger log = Logger.getRootLogger();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		HttpClientUtil.upload("/data/test.txt");
		HttpClientUtil.upload();
	}

	public static void upload() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpPost httppost = new HttpPost("http://localhost:9080/stonecloud/content/upload/upload");  
  
            FileBody bin = new FileBody(new File("/data/test.txt"));  
            FileBody bin2 = new FileBody(new File("/data/test2.txt")); 
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);  
  
            HttpEntity reqEntity = MultipartEntityBuilder.create()
            						.addPart("file", bin)
            						.addPart("file", bin2)
            						.addPart("comment", comment)
            						.addPart("comment", new StringBody("test", ContentType.TEXT_PLAIN))
            						.build();  
  
            httppost.setEntity(reqEntity);  
  
            log.info("executing request " + httppost.getRequestLine());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                log.info(response.getStatusLine());  
                HttpEntity repEntity = response.getEntity();  
                if (repEntity != null) {  
                	log.info(EntityUtils.toString(repEntity));
                }  
                EntityUtils.consume(repEntity);  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
	
	public static String get(String url){  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        String result = "";
        try {  
            HttpGet httpget = new HttpGet(url);  
  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                log.info(response.getStatusLine());  
                HttpEntity repEntity = response.getEntity();  
                if (repEntity != null) {  
                	result = EntityUtils.toString(repEntity);
                }
                EntityUtils.consume(repEntity);  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return result;
    }
	
	public static String post(String url, String param){  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        String result = "";
        try {  
            HttpPost httpPost = new HttpPost(url);  
            
//            StringBody comment = new StringBody(param, ContentType.TEXT_PLAIN); 
            
//            HttpEntity reqEntity = MultipartEntityBuilder.create().addTextBody("comment", param).build();
            HttpEntity entity = EntityBuilder.create().setText(param).build();
            
            httpPost.setEntity(entity);
  
            CloseableHttpResponse response = httpclient.execute(httpPost);  
            try {  
                log.info(response.getStatusLine());  
                HttpEntity repEntity = response.getEntity();  
                if (repEntity != null) {  
                	result = EntityUtils.toString(repEntity);
                }
                EntityUtils.consume(repEntity);  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return result;
    }

}
