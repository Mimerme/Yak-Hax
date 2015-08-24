package Yak_Hax.Yak_Hax_Mimerme;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import Yak_Hax.Yak_Hax_Mimerme.Exceptions.AuthorizationErrorException;
import Yak_Hax.Yak_Hax_Mimerme.Parse.ParseClient;

public class YikYakAPI {
	//TODO: Unify a better basecamp method, and remove it from the argument requirements
	//It is recommended to use an external JSON parsing library
	//Yak-Hax does not parse the JSON for you
	//Check documentation on parsing the JSONs

	//TODO: API still needs testing to confirm all methods work successfully

	static String YIKYAK_VERSION = "2.8.2";
	
	static final String BASE_URL = "https://us-central-api.yikyakapi.net";
	static final String BASE_ENCODER_URL = "https://yakhax-encoder.herokuapp.com/?message=";
	
	static final String API_VERSION = "0.9.8.7a";

	public static String getAPIVersion(){
		return API_VERSION;
	}

	//Should only be called for compatibility purposes
	public static void setYikYakVersion(String version){
		YIKYAK_VERSION = version;
	}
	
	public static String getYikYakVersion(){
		return YIKYAK_VERSION;
	}

	public static boolean login(String userID, String token, String userAgent){
		YikYakProfile.USER_ID = userID;
		YikYakProfile.TOKEN = token;
		YikYakProfile.USER_AGENT = userAgent;
		return true;
	}

	//Gets all local Yaks
	public static Element getYaks(SortedMap<String, String> parameters) throws IOException{
		return makeRequest(parseGetQuery("getMessages", parameters));
	}

	//Loads a Yak and its comments
	public static Element getYakComments(SortedMap<String, String> parameters) throws IOException{
		return makeRequest(parseGetQuery("getComments", parameters));
	}

	public static Element upvoteComment(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "downvoteComment?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&commentID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element deleteComment(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "deleteComment?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&commentID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element downvoteComment(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "likeComment?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&commentID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element reportComment(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "likeComment?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&commentID=" + parameters.get(4) 
				+ "&messageID" + parameters.get(5) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element upvoteYak(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "likeMessage?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&messageID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element reportYak(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "reportMessage?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&messageID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	//TODO: Check if altitude is still a valid parameter
	public static Element postComment(ArrayList<String> parameters, Map<String, String> formParameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "downvoteMessage?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1)
				+ "&messageID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;
		formParameters.put("salt", salt);
		formParameters.put("hash", hashValue);
		formParameters.put("accuracy", parameters.get(0));


		return makePostRequest(request, formParameters);
	}

	public static Element downvoteYak(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "downvoteMessage?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&messageID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element deleteYak(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "deleteMessage2?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&messageID=" + parameters.get(4) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	//Loads an area's hot Yaks and its comments
	public static Element getAreaHot(SortedMap<String, String> parameters) throws IOException{
		return makeRequest(parseGetQuery("hot", parameters));
	}

	public static String[] registerNewUser() throws NoSuchAlgorithmException, IOException, SignatureException{

		final String deviceID = APIUtils.generateDeviceID();
		final String userID = APIUtils.generateDeviceID();
		String userAgent = APIUtils.generateRandomUserAgent();
		final String token = APIUtils.convertMD5(userAgent);
		
		System.out.println("New user is being generated with the following values");
		System.out.println("DeviceID: " + deviceID);
		System.out.println("UserID: " + userID);
		System.out.println("User Agent: " + userAgent + " " + YikYakAPI.getYikYakVersion());
		System.out.println("Token: " + token);
		
		YikYakProfile.USER_AGENT = userAgent;
		
		String result = makePostRequest(parseGetQuery("registerUser", new TreeMap<String, String>()
				{{
					put("accuracy", YikYakProfile.ACCURACY);
					put("bc", YikYakProfile.BASECAMP);
					put("lat", YikYakProfile.LAT);
					put("long", YikYakProfile.LONG);
					put("userLat", YikYakProfile.LAT);
					put("userLong", YikYakProfile.LONG);
					put("userID", userID);
					put("token", token);
					put("version", YikYakAPI.getYikYakVersion());
					put("deviceID", deviceID);
				}}), new TreeMap<String, String>(){{
					put("Accept-Encoding", "gzip");
					put("Connection", "Keep-Alive");
				}}).text();
				
		
		if(!result.equals("1")){
			System.out.println("There was an error registering the user");
			System.out.println("Code: " + result);
			return null;
		}
		System.out.println(result);
		
		YikYakAPI.createInstallation();
		return new String[]{
				userID,token,deviceID,userAgent + " " + YikYakAPI.getYikYakVersion()
		};
	}
	
	private static void createInstallation() throws IOException, SignatureException{
		ParseClient client = new ParseClient();

		LinkedHashMap<String, String> jsonData = new LinkedHashMap<String, String>()
				{{
					put("appIdentifier", "\"com.yik.yak\"");
					put("installationId", "\"" + YikYakProfile.PARSE_ID + "\"");
					put("parseVersion", "\"" + ParseClient.PARSE_VERSION + "\"");
					put("appVersion", "\"" + YikYakAPI.getYikYakVersion() + "\"");
					put("appName", "\"Yik Yak\"");
					put("timeZone", "\"America/New_York\"");
					put("deviceType", "\"android\"");
				}};	
				
				Pattern p = Pattern.compile("\"objectId\":\"([^\"]*)\"");
				final Matcher m = p.matcher(client.saveObject(jsonData, "create"));
				m.find();
				
		final LinkedHashMap<String, String> updateSubData = new LinkedHashMap<String, String>()
				{{
					put("__op", "\"AddUnique\"");
					put("objects", "[\"c" + YikYakProfile.USER_ID + "c\"]");
				}};	

		LinkedHashMap<String, String> updateData = new LinkedHashMap<String, String>()
				{{
					put("channels", APIUtils.buildJSON(updateSubData));
					put("objectId", "\"" + m.group(1) + "\"");
				}};	
				System.out.println(client.saveObject(updateData, "update"));
	}

	private static String parseGetQuery(String requestType, SortedMap<String, String> parameters){
		String query = "/api/" + requestType + "?";
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			query += pair.getKey() + "=" + pair.getValue() + "&";
			it.remove();
		}
		query = query.substring(0, query.length() - 1);

		String salt = getSalt();
		String hashValue = getHash(query + salt);

		String request = BASE_URL;
		request += query + "&salt=" + salt + "&hash=" + hashValue;

		return request;
	}

	private static String[] parsePostQuery(String requestType, SortedMap<String, String> parameters){
		String query = "/api/" + requestType + "?";
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			query += pair.getKey() + "=" + pair.getValue() + "&";
			it.remove();
		}
		query = query.substring(0, query.length() - 1);

		String salt = getSalt();
		String hashValue = getHash(query + salt);

		return new String[]{salt, hashValue};
	}

	public static Element getAreaTop(SortedMap<String, String> parameters) throws IOException{
		return makeRequest(parseGetQuery("top", parameters));
	}

	public static Element getMyRecentYaks(SortedMap<String, String> parameters) throws IOException{
		return makeRequest(parseGetQuery("getMyRecentYaks", parameters));
	}

	public static Element getMyTops(SortedMap<String, String> parameters) throws IOException{
		return makeRequest(parseGetQuery("getMyTops", parameters));
	}

	public static Element getMyRecentReplies(SortedMap<String, String> parameters) throws IOException{
		return makeRequest(parseGetQuery("getMyRecentReplies", parameters));
	}

	//Verify your account
	public static String startVerifyAccount(final String token, String number, String c3Code, String prefix) throws IOException{
		SortedMap query = new TreeMap<String, String>()
				{{
					put("userID", "124123124112");
					put("version", YikYakAPI.getYikYakVersion());
					put("token", token);
				}};
				String requestURL = parseGetQuery("startVerification", query);

				return PostRequest
				.PostBodyRequest(requestURL, "{\"type\": \"sms\",\"number\": \"" + number + "\",\"country3\": \""+ c3Code +"\",\"prefix\": \"" + prefix + "\"}" , YikYakProfile.USER_AGENT + " " + YikYakAPI.getYikYakVersion());
	}
	
	public static String verifyAccount(final String userID, final String userToken, String verificationToken, String verificationCode) throws IOException{
		SortedMap query = new TreeMap<String, String>()
				{{
					put("userID", userID);
					put("version", YikYakAPI.getYikYakVersion());
					put("token", userToken);
				}};
				String requestURL = parseGetQuery("verify", query);
				
				String json = "{\"token\": \"" + verificationToken + "\",\"userID\": \"" + userID + "\",\"code\": \"" + verificationCode + "\"}";
				
				return PostRequest.PostBodyRequest(requestURL,json , YikYakProfile.USER_AGENT + " " + YikYakAPI.getYikYakVersion());
	}

	public static Element postYak(SortedMap<String, String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "sendMessage?";

		hashMessage += "bc=0&" + "token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&version=" + YikYakAPI.YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		parameters.put("salt", salt);
		parameters.put("hash", hashValue);
		
		return makePostRequest(request, parameters);
	}

	//Makes the request to the YikYak API
	private static Element makeRequest(String request){
		System.out.println(request);
		try {
			return Jsoup.connect(request)
					.userAgent(YikYakProfile.USER_AGENT + " " + YikYakAPI.getYikYakVersion())
					.ignoreContentType(true)
					.timeout(60 * 1000)
					.get()
					.body();
		} catch (HttpStatusException e) {
			// TODO Auto-generated catch block
			if(e.getStatusCode() == 401){
				
				try {
					throw new AuthorizationErrorException("A 500 error has occured the request was not authorized");
				} catch (AuthorizationErrorException e1) {
					e1.printStackTrace();
				}
				
				System.out.println("A 401 exception has occured, slow down your requests and"
						+ " double check your query parameters");
				return null;
			}
			else if(e.getStatusCode() == 500){
					try {
						throw new AuthorizationErrorException("A 500 error has occured the request was not authorized");
					} catch (AuthorizationErrorException e1) {
						e1.printStackTrace();
					}
			}
			else{
				e.printStackTrace();
			}

		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static Element makePostRequest(String request, Map<String, String> formParameters){
		System.out.println(request);
		
		try {
			return Jsoup.connect(request)
					.userAgent(YikYakProfile.USER_AGENT + "" + YikYakAPI.getYikYakVersion())
					.ignoreContentType(true)
					.timeout(60 * 1000)
					.data(formParameters)
					.post()
					.body();
		} catch (HttpStatusException e) {
			// TODO Auto-generated catch block
			if(e.getStatusCode() == 401){
				System.out.println("A 401 exception has occured, slow down your requests and"
						+ " double check your query parameters");
			}
			else if(e.getStatusCode() == 500){
				try {
					throw new AuthorizationErrorException(e.getMessage());
				} catch (AuthorizationErrorException e1) {
					e1.printStackTrace();
				}
			}
			else{
				e.printStackTrace();
			}

		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String getSalt(){
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	//Get HASH value from server
	@SuppressWarnings("deprecation")
	public static String getHash(String message){
		System.out.println("Getting the HASH value of " + message);
		try {
			String hash = Jsoup.connect(BASE_ENCODER_URL + URLEncoder.encode(message)).get()
					.text();
			return hash;
		} 
		catch(SocketTimeoutException e){
			System.out.println("There was a SocketTimeoutException, wait for the HASH server "
					+ "to wake up");
			return "SocketTimeout";
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
