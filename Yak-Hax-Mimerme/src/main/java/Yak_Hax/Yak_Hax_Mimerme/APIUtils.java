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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class APIUtils {
	//Utilities to aid the YikYak API including calculations

	private static Random rand = new Random();

	//Ported from soren121's repository
	//https://github.com/soren121/yodel/blob/64a42feff48ffa66c88b1645f68f69ccda12a422/Yodel/js/yak_api.js
	//Composes a user Agent with the proper format
	public static String generateRandomUserAgent() {

		//The base String for the User Agent by stating some user data
		String[] base = new String[3];
		base[0] = "Dalvik/1.6.0 (Linux; U; Android 4.4.4; ";
		base[1] = " Build/";
		base[2] = ")";

		//A list of possible devices
		String[] devices = {
				"Nexus 4",
				"Nexus 5",
				"HTC One_M8",
				"SM-N900V",
				"XT1080",
				"SM-G900V",
				"SCH-I545"
		};

		// Select random device name
		String device = devices[rand.nextInt(devices.length)];
		// Generate random build ID
		ArrayList<String> build = generateRandom(2);
		String buildString = "";
		for (String s : build)
		{
			buildString += s + ",";
		}
		buildString = buildString.substring(0, 5).toUpperCase();

		String userAgent = base[0] + device + base[1] + buildString + base[2];

		return userAgent;
	}

	//Generates a random set of integers to create a buildID
	public static ArrayList<String> generateRandom(int num){
		int[] buf = new int [8];
		for(int i = 0; i < buf.length; i++){
			buf[i] = rand.nextInt(65536);
		}
		ArrayList<String> arr = new ArrayList<String>();

		// Decrement num by one to align with zero-indexed array
		num--;
		for (int cur = 0; cur <= num; cur++) {
			// Get a chunk of the crypto buffer and force to string
			String ret = String.valueOf(buf[cur]);
			while (ret.length() < 4) {
				ret = "0" + ret;
			}
			arr.add(ret);
		}

		return arr;
	}
	
	//Generates a UUID based on the default Java library
	public static String generateUUID(){
		return UUID.randomUUID().toString();
	}
	
	//Converts a message to an MD5
	public static String convertMD5(String message) throws NoSuchAlgorithmException{
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		md5Digest.update(message.getBytes());
		byte md5Data[] = md5Digest.digest();
		
		//Convert md5 to hex
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md5Data.length; i++) {
         sb.append(Integer.toString((md5Data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString().toUpperCase();
	}
	
	//Builds a JSON from a Map (TreeMap, HashMap, LinkedHashMap, etc.)
	public static String buildJSON(Map<String, String> json){
		String temp = "{";
		
		//Iterate 
		for(Map.Entry<String,String> entry : json.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
 
			temp += "\"" + key + "\":" + value + ",";
		}
		temp = temp.substring(0, temp.length() - 1);
		return temp + "}";
	}
	
	//Generates a device ID by converting a UUID to a MD5 hash
	public static String generateDeviceID() throws NoSuchAlgorithmException{
		return convertMD5(generateUUID());
	}

}
