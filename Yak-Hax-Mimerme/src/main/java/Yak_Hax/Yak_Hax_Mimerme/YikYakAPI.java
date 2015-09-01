/*The MIT License (MIT)

Copyright (c) 2015 Andros Yang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

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

import Yak_Hax.Yak_Hax_Mimerme.Exceptions.RequestException;
import Yak_Hax.Yak_Hax_Mimerme.Exceptions.SleepyServerException;
import Yak_Hax.Yak_Hax_Mimerme.Parse.ParseClient;

public class YikYakAPI {
	//It is recommended to use an external JSON parsing library
	//Yak-Hax does not parse the JSON for you
	//Check documentation on parsing the JSONs
	//Since you seem to be reading a code maybe my future blog post might be an interesting read.

	//TODO: API still needs testing to confirm all methods work successfully

	static String YIKYAK_VERSION = "2.9.2";

	//YikYak EndPoint
	static final String BASE_URL = "https://us-central-api.yikyakapi.net";
	//HASH server
	static final String BASE_ENCODER_URL = "https://yakhax-encoder.herokuapp.com/?message=";

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
	public static Element getYaks(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("getMessages", parameters));
	}

	//Loads a Yak and its comments
	public static Element getYakComments(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("getComments", parameters));
	}

	public static Element upvoteComment(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("likeComment", parameters));
	}

	public static Element deleteComment(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("deleteComment", parameters));
	}

	public static Element downvoteComment(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("downvoteComment", parameters));
	}

	public static Element reportComment(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("reportMessage", parameters));
	}

	public static Element upvoteYak(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("likeMessage", parameters));
	}
	
	public static Element reportYak(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("reportMessage", parameters));
	}

	public static Element downvoteYak(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("downvoteMessage", parameters));
	}

	//Posts a comment
	public static Element postComment(SortedMap<String, String> queryparameters, SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makePostRequest(parsePostQuery("postComment", queryparameters, parameters), parameters);
	}

	//Deletes one of your yaks
	public static Element deleteYak(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("deleteMessage2", parameters));
	}

	//Loads an area's hot Yaks and its comments
	public static Element getAreaHot(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("hot", parameters));
	}

	//Register a new user
	public static String[] registerNewUser() throws NoSuchAlgorithmException, IOException, SignatureException, SleepyServerException, RequestException{

		final String deviceID = APIUtils.generateDeviceID();
		final String userID = APIUtils.generateDeviceID();
		String userAgent = APIUtils.generateRandomUserAgent();
		//Tokens are an MD5 of the User Agent
		final String token = APIUtils.convertMD5(userAgent);

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
		YikYakAPI.createInstallation();
		return new String[]{
				userID,token,deviceID,userAgent + " " + YikYakAPI.getYikYakVersion()
		};
	}

	//Creates a new user installation with the Parse server
	private static void createInstallation() throws IOException, SignatureException, SleepyServerException{
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
	}

	private static String parseGetQuery(String requestType, SortedMap<String, String> parameters) throws SleepyServerException{
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

	private static String parsePostQuery(String requestType, SortedMap<String, String> queryParams, SortedMap<String, String> params) throws SleepyServerException{
		String request, hashMessage;

		request = BASE_URL;
		
		hashMessage = "/api/" + requestType + "?";
		Iterator it = queryParams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			hashMessage += pair.getKey() + "=" + pair.getValue() + "&";
			it.remove();
		}
		
		hashMessage += "token=" + YikYakProfile.TOKEN
				+ "&userID=" + YikYakProfile.USER_ID + "&version=" + YikYakAPI.getYikYakVersion();

		String salt = getSalt();
		String hashValue = getHash(hashMessage + salt);

		request += hashMessage + "&salt=" + salt + "&hash=" + hashValue;

		params.put("salt", salt);
		params.put("hash", hashValue);
		return request;
	}

	public static Element getAreaTop(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("top", parameters));
	}

	public static Element getMyRecentYaks(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("getMyRecentYaks", parameters));
	}

	public static Element getMyTops(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("getMyTops", parameters));
	}

	public static Element getMyRecentReplies(SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makeRequest(parseGetQuery("getMyRecentReplies", parameters));
	}
	
	//Generic GET request for features not yet developed in the API
	public static Element getRequest(String target, SortedMap<String, String> parameters) throws RequestException, SleepyServerException{
		return makeRequest(parseGetQuery(target, parameters));
	}
	
	//Generic POST request for features not yet developed in the API
	public static Element postRequest(String target, SortedMap<String, String> queryparameters, SortedMap<String, String> parameters) throws RequestException, SleepyServerException{
		return makePostRequest(parsePostQuery(target, queryparameters, parameters), parameters);
	}

	//This must be called first to get the verification token
	//csCode - https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3
	//prefix - https://en.wikipedia.org/wiki/List_of_country_calling_codes
	//number - (xxx) xxx-xxxx
	//Start to verify your account
	public static String startVerifyAccount(final String token, String number, String c3Code, String prefix) throws IOException, SleepyServerException{
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

	
	//Verify the current user
	//verificationToken - token from startVerifyAccount
	//verificationCode - code sent to the phone
	public static String verifyAccount(final String userID, final String userToken, String verificationToken, String verificationCode) throws IOException, SleepyServerException{
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
  
	//Posts a yak
	public static Element postYak(SortedMap<String, String> queryparameters, SortedMap<String, String> parameters) throws IOException, SleepyServerException, RequestException{
		return makePostRequest(parsePostQuery("sendMessage", queryparameters, parameters), parameters);
	}

	//Makes a get request to the YikYak API
	private static Element makeRequest(String request) throws RequestException{
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
					throw new RequestException("401 error has occured the request was denied");
			}
			else if(e.getStatusCode() == 500){
				throw new RequestException("A 500 error has occured the request was not authorized");
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

	//Makes a post request
	private static Element makePostRequest(String request, Map<String, String> formParameters) throws RequestException{
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
				throw new RequestException("401 error has occured the request was denied");
			}
			else if(e.getStatusCode() == 500){
				throw new RequestException("A 500 error has occured the request was not authorized");
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

	//Current time in seconds, used by the server to make sure the request is not too old
	//There is lee-way time before a salt becomes invalid to support users with slow connections
	private static String getSalt(){
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	//Get signed HASH value from the server
	@SuppressWarnings("deprecation")
	public static String getHash(String message) throws SleepyServerException{
		try {
			String hash = Jsoup.connect(BASE_ENCODER_URL + URLEncoder.encode(message)).get()
					.text();
			return hash;
		} 
		catch(SocketTimeoutException e){
			throw(new SleepyServerException("There was a SocketTimeout wait for the HASH server to wake up"));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
