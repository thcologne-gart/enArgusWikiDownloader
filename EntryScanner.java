package enArgusWkiParser;

import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class EntryScanner extends Functional {
	public static void main( String[] args ) {
		
		printFile();

		ArrayList<String> headlineList = getHeadlineList(headlinesUrl);
		
		for (int i = 0; i < headlineList.size(); i++) {
			String url = blankWikiUrl + headlineList.get(i);
						
			try {
				String fullEntry = parseWikiItem(getJsonObject(url), "html");
				String processedEntry = processEntry(fullEntry);
				System.out.println(processedEntry);
			} catch (Exception e) {
				System.out.println(url);
				System.out.println("    FAIL");
			}
		}
	}

}
