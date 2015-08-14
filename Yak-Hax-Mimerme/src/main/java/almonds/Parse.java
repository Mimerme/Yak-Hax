package almonds;

public class Parse 
{
	private static String mApplicationId;
	private static String mRestAPIKey;
	
	private static final String PARSE_API_URL = "https://api.parse.com";
	private static final String PARSE_API_URL_CLASSES = "/1/classes/";
	
	/**
	 * @param applicationId
	 * @param restAPIKey
	 */
	static public void initialize(String applicationId, String restAPIKey)
	{
		mApplicationId = applicationId;
		mRestAPIKey = restAPIKey;
	}
	
	static public String getApplicationId() {return mApplicationId;}
	static public String getRestAPIKey() {return mRestAPIKey;}
	static public String getParseAPIUrl() {return PARSE_API_URL;}
	static public String getParseAPIUrlClasses() {return getParseAPIUrl() + PARSE_API_URL_CLASSES;}
}
