package enArgusWkiParser;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Settings {
	
//	static String language = "de";
	static String language = "en";
	
	static String headlinesUrl = "https://www.enargus.de/pub/bscw.cgi/REST/enargus.pagelist?lang=" + language;
	static String blankWikiUrl = "https://www.enargus.de/pub/bscw.cgi/REST/enargus.page?lang=" + language + "&mode=1&page=";
	
	
	
}
