package Yak_Hax.Yak_Hax_Mimerme;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import org.jsoup.Connection.KeyVal;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class YikYakAPI {
	//TODO: Unify a better basecamp method, and remove it from the argument requirements
	//It is recommended to use an external JSON parsing library
	//Yak-Hax does not parse the JSON for you
	//Check documentation on parsing the JSONs
	
	//TODO: API still needs testing to confirm all gears/methods work succesfully
	
	static final String BASE_URL = "https://us-central-api.yikyakapi.net";
	static final String BASE_ENCODER_URL = "https://yakhax-encoder.herokuapp.com/?message=";

	static final String YIKYAK_VERSION = "2.8.1";
	
	//Gets all local Yaks
	public static Element getYaks(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "getMessages?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&long=" + parameters.get(3) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}
	
	//Loads a Yak and its comments
	public static Element getYakComments(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "getComments?";

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
	public static Element getAreaHot(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "hot?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&long=" + parameters.get(3) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}
	
	public static String[] registerNewUser(ArrayList<String> parameters) throws NoSuchAlgorithmException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "registerUser?";

		String deviceID = APIUtils.generateDeviceID();
		String userID = APIUtils.generateDeviceID();
		String userAgent = APIUtils.generateRandomUserAgent();
		String token = APIUtils.convertMD5(userAgent);
		
		hashMessage += "accuracy=" + parameters.get(0)
				+ "&deviceID=" + deviceID + "&lat=" + parameters.get(2)
				+ "&long=" + parameters.get(3) + "&token=" + token
				+ "&userID=" + userID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		System.out.println("New user is being generated with the following values");
		System.out.println("DeviceID: " + deviceID);
		System.out.println("UserID: " + userID);
		System.out.println("User Agent: " + userAgent);
		System.out.println("Token: " + token);

		return new String[]{
				deviceID,userID,userAgent,token
		};
	}
	
	//Loads an area's hot Yaks and its comments
	public static Element getAreaTop(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "top?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&long=" + parameters.get(3) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}
	
	public static Element getMyRecentYaks(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "getMyRecentYaks?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&long=" + parameters.get(3) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element getMyTops(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "getMyTops?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&long=" + parameters.get(3) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}
	
	public static Element getMyRecentReplies(ArrayList<String> parameters) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "getMyRecentReplies?";

		hashMessage += "accuracy=" + parameters.get(0)
				+ "&bc=" + parameters.get(1) + "&lat=" + parameters.get(2)
				+ "&long=" + parameters.get(3) + "&token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&userLat=" + parameters.get(2)
				+ "&userLong=" + parameters.get(3) + "&version=" + YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		return makeRequest(request);
	}

	public static Element postYak(Map<String, String> parameters) throws IOException{
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
	
	//Convert the message to something for the request
	private static String convertMessage(String message){
		return message.replaceAll(" ", "+");
	}
	
	//Makes the request to the YikYak API
	private static Element makeRequest(String request){
		System.out.println(request);

		System.out.println("Response value:");
		try {
			return Jsoup.connect(request)
					.userAgent(APIUtils.generateRandomUserAgent())
					.ignoreContentType(true)
					.timeout(60 * 1000)
					.get()
					.body();
		} catch (HttpStatusException e) {
			// TODO Auto-generated catch block
			if(e.getStatusCode() == 401){
				System.out.println("A 401 exception has occured, slow down your requests and"
						+ " double check your query parameters");
				return null;
			}
			else if(e.getStatusCode() == 500){
				System.out.println("A 500 exception has occured, there may have been a problem with the User Agent"
						+ " post this issue on GitHub");
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
	
	private static Element makePostRequest(String request, Map<String, String> formData){
		System.out.println(request);

		System.out.println("Response value:");
		try {
			return Jsoup.connect(request)
					.userAgent(APIUtils.generateRandomUserAgent())
					.ignoreContentType(true)
					.timeout(60 * 1000)
					.data(formData)
					.post()
					.body();
		} catch (HttpStatusException e) {
			// TODO Auto-generated catch block
			if(e.getStatusCode() == 401){
				System.out.println("A 401 exception has occured, slow down your requests and"
						+ " double check your query parameters");
				return null;
			}
			else if(e.getStatusCode() == 500){
				System.out.println("A 500 exception has occured, there may have been a problem with the User Agent"
						+ " post this issue on GitHub");
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
			System.exit(1);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
