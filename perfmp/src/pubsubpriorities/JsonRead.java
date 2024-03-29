package pubsubpriorities;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonRead {
	
	private static final boolean True = false;

	public static JSONArray getJSONArray(String file, String listname) {

		JSONParser parser = new JSONParser();
		Object obj;
		JSONArray listArray = null;

		try {
			obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;

			listArray = (JSONArray) jsonObject.get(listname);

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listArray;
	}
	
	public static double getParam(String file, String panamname) {

		JSONParser parser = new JSONParser();
		Object obj;
		double param = 0;

		try {
			obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;
			
			param = (double) jsonObject.get(panamname);

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return param;
	}
	
	public static int getParamInt(String file, String panamname) {

		JSONParser parser = new JSONParser();
		Object obj;
		int param = 0;

		try {
			obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;
			
			String param_str = (String) jsonObject.get(panamname);
			
			param =  Integer.parseInt(param_str);;

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return param;
	}
	
	public static String getStringParam(String file, String panamname) {

		JSONParser parser = new JSONParser();
		Object obj;
		String param = "";

		try {
			obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;
			
			param = (String) jsonObject.get(panamname);

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return param;
	}

}
