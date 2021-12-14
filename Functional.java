package enArgusWkiParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Functional extends Settings{

	// get JSON-Object of a Wiki-Entry
	public static JsonObject getJsonObject(String Url) {
		//		System.out.println("getJsonArray");
		JsonObject JsonObject = null;
		try {
			// make Url an URL
			URL url = new URL(Url);
			// Connect to server
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// http-GET from connected server
			con.setRequestMethod("GET");
			// get response code
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success		
				// read in the stream
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				// close input stream
				in.close();
				// convert input stream to JSON-Array
				JsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
				return JsonObject;	
			}
		} catch (IOException e) {
			return JsonObject;
		}
		return JsonObject;
	}
	
	// get JSON-Array of the Entry-List
	public static JsonArray getJsonArray(String Url) {
//				System.out.println("getJsonArray");
		JsonArray JsonArray = null;
		try {
			// make Url an URL
			URL url = new URL(Url);
			// Connect to server
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// http-GET from connected server
			con.setRequestMethod("GET");
			// get response code
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success		
				// read in the stream
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				// close input stream
				in.close();
				// convert input stream to JSON-Array
				JsonArray = new Gson().fromJson(response.toString(), JsonArray.class);
				return JsonArray;	
			}
		} catch (IOException e) {
			return JsonArray;
		}
		return JsonArray;
	}
	
	public static String parseWikiItem(JsonObject jsonObject, String key) {
		JsonElement html = jsonObject.get(key);
		return html.toString(); 
	}
		
	public static ArrayList<Document> convertJsonArrayToArrayList(JsonArray jsonArray){
		ArrayList<Document> list = new ArrayList<Document>();

		for (int i = 0; i < jsonArray.size() ; i++) { 		
			String jsonStr = jsonArray.get(i).toString();
			Document doc = Document.parse(jsonStr);
			list.add(doc);
		}
		
		return list;
	}
	
	public static ArrayList<String> getHeadlineList(String url) {
		ArrayList<String> headlines = new ArrayList<>();
		
		JsonArray entryJsonArray = getJsonArray(url);
				
		for (int i = 0; i < entryJsonArray.size(); i++) {

			String headline = parseWikiItem((JsonObject) entryJsonArray.get(i), "name").replace("\"", "").replace(" ", "%20").replace("ä", "%C3%A4").replace("Ä", "%C3%84").replace("ö", "%C3%B6").replace("Ö", "%C3%96").replace("ü", "%C3%BC").replace("Ü", "%C3%9C").replace("°", "%C2%B0").replace("ß", "%C3%9F");
			headlines.add(headline);
		}
		return headlines;
	}

	
	// processing the string
	
	public static String processEntry(String fullEntry) {
		String string1 = cutHeadlineFromWiki(fullEntry);

		String string2 = cutEnglish(string1);

		String string3 = deleteBrackets(string2);
		
		String string4 = deleteBackslashes(string3);
		
		return string4;
	}

	public static String cutHeadlineFromWiki(String entry) {
		String [] arrayString = entry.split("<p>");
		String wikiText = new String();
		for (int i = 0; i < arrayString.length; i++) {
			if (i!=0) {
				wikiText += arrayString[i];
			}
		}
		return wikiText;
	}

	public static String cutEnglish(String entry) {
		String [] arrayString = entry.split("Englische Übersetzung");
		String wikiText = new String();
		for (int i = 0; i < arrayString.length; i++) {
			if (i<1) {
				wikiText += arrayString[i];
			}
		}
		return wikiText;
	}

	public static String deleteBrackets(String entry) {
		boolean checker = false;
		boolean runner = true; 
		int counter = 0 ; 
		String newString = "";

		while (runner) {
			
			if (counter >= entry.length()) {
				break; 
			}

			if (entry.charAt(counter) == '<') {				
				checker = true; 
			}
			
			if (entry.charAt(counter) == '>') {				
				checker = false; 
			}
			
			if (checker == false && entry.charAt(counter) != '>' ) {
				newString += entry.charAt(counter);
			}
			
			counter++;
		}
		return newString; 
	}

	private static String deleteBackslashes(String entry) {
		String newString1;
		String newString2;
		newString1 = entry.replace("\\r", "");
		newString2 = newString1.replace("\\n", "");
		return newString2; 
	}

	
	// console output stream into text-file
	public static void printFile() {
		System.out.println("writing txt-file...");
		File file = new File("enArgus_wiki_" + language + ".txt");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
		} catch (FileNotFoundException e1) {
			System.out.println("File-Output hat nicht geklappt");			
			e1.printStackTrace();
		}
	}
	
	
	
}
