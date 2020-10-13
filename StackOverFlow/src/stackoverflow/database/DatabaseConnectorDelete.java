package stackoverflow.database;
import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.Log;

public class DatabaseConnectorDelete {
	private String apiResponse = "no response";
	protected String userID;
	protected Account account = new Account();
	
	public String databaseWriter(String json,String apiName)throws JSONException, IOException  {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String baseURL= account.getDatabaseURL()+"/api/";
			String url = baseURL+apiName;
		    HttpDelete request = new HttpDelete(url);
		    StringEntity params = new StringEntity(json);
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    CloseableHttpResponse response = httpClient.execute(request);
		    System.out.println(response);
		    apiResponse = response.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			new Log().saveLog(ex);
		} finally {
		    httpClient.close();
		}
		return apiResponse;
	}
}
