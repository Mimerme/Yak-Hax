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
	
	public static Element getMessages(ArrayList<String> parameters) throws IOException{
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

	private static Element makeRequest(String request){
		System.out.println(request);

		System.out.println("Response value:");
		try {
			return Jsoup.connect(request)
					.userAgent("Dalvik/2.1.0 (Linux; U; Android 5.0.2; SM-G925V Build/LRX22G) 2.8.1")
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
