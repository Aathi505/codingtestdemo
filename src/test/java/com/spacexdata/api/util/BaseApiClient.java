package com.spacexdata.api.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Reporter;

public class BaseApiClient {

   
	
	public static final Map<String, String> RESPONSE_HEADERS = new HashMap<String,String>();
	
	private static int status;
	private static final int TIMEOUT_MILLIS = 600000;
	public CloseableHttpClient httpClient = buildHttpClient();
	
	private CloseableHttpClient buildHttpClient() {
        return HttpClients.createDefault();
	}
	
	public String getResponse(String url) {

		HttpGet httpGet = new HttpGet(url);

		// To set timeout
		httpGet.setConfig(getRequestConfig());

		ResponseHandler<String> responseHandler = getResponseHandler();

		try {
			return httpClient.execute(httpGet, responseHandler);

		} catch (IOException e) {
			Reporter.log("Exception Occurred for URL "+url+"\n Error: " + e);
		}

		return null;
	}
	
	private void setHeaders(HttpRequestBase httpRequest, List<Header> headers) {
		for (Header header : headers) {
			httpRequest.addHeader(header.getName(), header.getValue());
		}
	}
	
	private RequestConfig getRequestConfig() {
		return RequestConfig.custom().setSocketTimeout(TIMEOUT_MILLIS).setConnectTimeout(TIMEOUT_MILLIS)
				.setConnectionRequestTimeout(TIMEOUT_MILLIS).build();
	}
	
	private ResponseHandler<String> getResponseHandler() {
		return new ResponseHandler<String>() {

			public String handleResponse(HttpResponse httpResponse) throws IOException {

				 status = httpResponse.getStatusLine().getStatusCode();

				if (status >= 200 && status < 300) {

					System.out.println("URL: ");
					System.out.println("returned code: " + httpResponse.getStatusLine().getStatusCode() + " "
							+ httpResponse.getStatusLine().getReasonPhrase());
					

					for (Header header : httpResponse.getAllHeaders()) {
						RESPONSE_HEADERS.put(header.getName(), header.getValue());

						System.out.println(header);
					}
					HttpEntity entity = httpResponse.getEntity();

					return entity != null ? EntityUtils.toString(entity,StandardCharsets.UTF_8) : null;
				} else if(status==404){
					return null;
				}else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}
		};
	}
	
	public int getStatusCode(){
		return status;
	}
}
