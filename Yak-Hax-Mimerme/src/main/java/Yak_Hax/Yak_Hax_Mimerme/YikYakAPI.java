package Yak_Hax.Yak_Hax_Mimerme;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class YikYakAPI {
	static final String BASE_URL = "https://us-central-api.yikyakapi.net";
	//use the dns found IP to prevent DNS lookup errors (it happens on my network ;_;)
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

	public static Element postYak(ArrayList<String> parameters, ArrayList<String> postData) throws IOException{
		String request, hashMessage;

		request = BASE_URL;
		hashMessage = "/api/";

		hashMessage += "sendMessage?";

		hashMessage += "token=" + parameters.get(0)
				+ "&userID=" + parameters.get(1) + "&version=" + YikYakAPI.YIKYAK_VERSION;

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += "bc" + postData.get(0) + "bypassedThreatPopup" + postData.get(1)
				+ "hash=" + hashValue + "&lat=" + postData.get(2) + "&long=" + postData.get(3)
				+ "&message=" + convertMessage(postData.get(4)) + "&salt=" + salt + "&token=" + parameters.get(0)
				+ "&userID=" + parameters.get(1) + "&version=" + YikYakAPI.YIKYAK_VERSION;

		return makeRequest(request);
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
	
	private static Element makePostRequest(String request){
		System.out.println(request);

		System.out.println("Response value:");
		try {
			return Jsoup.connect(request)
					.userAgent(APIUtils.generateRandomUserAgent())
					.ignoreContentType(true)
					.timeout(60 * 1000)
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
	private static String getHash(String message){
		System.out.println("Getting the HASH value of " + message);
		try {
			String hash = Jsoup.connect(BASE_ENCODER_URL + URLEncoder.encode(message)).get()
					.text();
			return hash;
		} 
		catch(SocketTimeoutException e){
			System.out.println("There was a SocketTimeoutException, wait for the HASH server "
					+ "to wake up");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
