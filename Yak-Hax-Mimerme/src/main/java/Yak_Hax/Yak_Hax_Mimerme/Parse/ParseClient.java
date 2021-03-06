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
SOFTWARE.*/

package Yak_Hax.Yak_Hax_Mimerme.Parse;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.jsoup.Jsoup;

import Yak_Hax.Yak_Hax_Mimerme.APIUtils;
import Yak_Hax.Yak_Hax_Mimerme.PostRequest;
import Yak_Hax.Yak_Hax_Mimerme.YikYakAPI;
import Yak_Hax.Yak_Hax_Mimerme.YikYakProfile;
import Yak_Hax.Yak_Hax_Mimerme.Exceptions.SleepyServerException;

public class ParseClient {

	static final String BASE_ENCODER_URL = "https://yakhax-encoder.herokuapp.com/parse?message=";

	public static String PARSE_VERSION = "1.7.1";
	static String PARSE_PACKAGE_BUILD = "64";

	static String PARSE_ENDPOINT = "https://api.parse.com/2/";

	static final String PARSE_USER_AGENT = "Parse Android SDK " + PARSE_VERSION + "(com.yik.yak/" + PARSE_PACKAGE_BUILD + ") API Level 21";

	public ParseClient() throws IOException{

		YikYakProfile.PARSE_ID = APIUtils.generateUUID().toLowerCase();

	}

	//Generate a 20 digit random number
	//Actually a bit more difficult than you think
	private String generateNonce() { 
		return String.valueOf(((long)(Math.random()*10000000000L)) + ((long)(Math.random()*10000000000L)) * 10000000000L);
	}

	//Save the object with the Parse server
	public String saveObject(final Map<String, String> params, String target) throws IOException, SignatureException, SleepyServerException{
		
		String paramString = "";

		//OAuth data to make the server happy
		LinkedHashMap<String, String> oAuthData = new LinkedHashMap<String, String>()
				{{
					put("oauth_consumer_key", YikYakProfile.PARSE_APPLICAITON_KEY);
					put("oauth_version", "1.0");
					put("oauth_signature_method", "HMAC-SHA1");
					put("oauth_timestamp", String.valueOf(System.currentTimeMillis() / 1000));
					put("oauth_nonce", generateNonce());
				}};

				TreeMap<String, String> sortedOAuthData = new TreeMap<>(oAuthData);

				for(Map.Entry<String,String> entry : sortedOAuthData.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();

					paramString += URLEncoder.encode(key) + "=" + URLEncoder.encode(value) + "&";
				}

				paramString = paramString.substring(0, paramString.length() - 1);

				//Generate the signature
				String oAuthSig = generateSignature("POST&" + URLEncoder.encode(PARSE_ENDPOINT + target) + "&" + URLEncoder.encode(paramString));
				oAuthData.put("oauth_signature", oAuthSig);

				//String that has gone through signing
				String signString = "";
				final String authString;

				for(Map.Entry<String,String> entry : oAuthData.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();

					signString += URLEncoder.encode(key) + "=\"" + URLEncoder.encode(value) + "\", ";
				}

				authString = "OAuth " + signString.substring(0, signString.length() - 2);

				LinkedHashMap<String, String> json = new LinkedHashMap<String, String>()
						{{
							put("classname", "\"_Installation\"");
							put("data", APIUtils.buildJSON(params));
							put("osVersion", "\"5.0.2\"");
							put("appBuildVersion", "\"" + PARSE_PACKAGE_BUILD + "\"");
							put("appDisplayVersion", "\"" + YikYakAPI.getYikYakVersion() + "\"");
							put("v", "\"a" + PARSE_VERSION + "\"");
							put("iid", "\"" + YikYakProfile.PARSE_ID + "\"");
							put("uuid", "\"" + APIUtils.generateUUID().toLowerCase() + "\"");
						}};

						return PostRequest.PostBodyRequest(PARSE_ENDPOINT + target, APIUtils.buildJSON(json), new TreeMap<String, String>(){{
							put("Accept-Encoding", "gzip");
							put("Authorization", authString);
							put("User-Agent", PARSE_USER_AGENT);
							put("Content-Type", "application/json");
						}});
	}

	//Generates the signature via the server
	private static String generateSignature(String message) throws SleepyServerException{
		try {
			String hash = Jsoup.connect(BASE_ENCODER_URL + URLEncoder.encode(message)).get()
					.text();
			return hash;
		}
		catch(SocketTimeoutException e){
			throw new SleepyServerException("There was a SocketTimeout wait for the HASH server to wake up");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
