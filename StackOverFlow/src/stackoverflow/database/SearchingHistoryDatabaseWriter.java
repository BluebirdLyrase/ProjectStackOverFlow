package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchingHistoryDatabaseWriter extends APIConnectorPost {
	private String jsonString = "";
	private String apiName = "addSearchingHistory";

	public SearchingHistoryDatabaseWriter(JSONObject newdata)throws JSONException, IOException {
		if(account.isLoggedIn()) {
		JSONObject json = newdata;
		userID = account.getUserID();
		json.put("UserID",userID);
		jsonString = json.toString();
		databaseWriter(jsonString, apiName);
		}
	}

}
