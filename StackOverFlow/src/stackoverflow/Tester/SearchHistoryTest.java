package stackoverflow.Tester;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.LocalJsonConnector.SearchingHistory;

public class SearchHistoryTest {
	
	public static void main(String[] args) throws IOException, JSONException {
		SearchingHistory s = new SearchingHistory();
		int x = s.getSearchingDate().length;
		
		String[] text = s.getSearchText();
		String[] date = s.getSearchingDate();
		
		for(int i = 0;i<x;i++) {
			System.out.println(text[i]+" : : "+date[i]);
		}
		
	}

}
