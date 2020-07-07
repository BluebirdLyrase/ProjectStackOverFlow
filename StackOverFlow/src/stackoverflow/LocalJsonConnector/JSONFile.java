package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.APIConnecter.StackOverFlowConnecter;

public class JSONFile {
	protected static final Logger LOGGER = Logger.getLogger(StackOverFlowConnecter.class.getName());
	
	protected JSONObject parseJSONFile(String filePath) throws JSONException, IOException {
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		return new JSONObject(content);
	}

	protected void saveJSONFile(String filePath,JSONObject JsonObject) throws JSONException, IOException {
		Files.writeString(Paths.get(filePath), JsonObject.toString(), StandardOpenOption.WRITE);
	}

}