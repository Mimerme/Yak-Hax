package Yak_Hax.Yak_Hax_Mimerme;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class APIUtils {
	//Utilities to aid the YikYak API

	private static Random rand = new Random();

	//Ported from soren121's repository
	//https://github.com/soren121/yodel/blob/64a42feff48ffa66c88b1645f68f69ccda12a422/Yodel/js/yak_api.js
	public static String generateRandomUserAgent() {

		String[] base = new String[3];
		base[0] = "Dalvik/1.6.0 (Linux; U; Android 4.4.4; ";
		base[1] = " Build/";
		base[2] = ")";

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
	
	public static String generateUUID(){
		ArrayList<String> random = generateRandom(8);
        return (random.get(0) + random.get(1) + "-" + random.get(2) + "-" + random.get(3) + "-" + random.get(4) + "-" 
		+ random.get(5) + random.get(6) + random.get(7)).toUpperCase();
	}
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
	public static String generateDeviceID() throws NoSuchAlgorithmException{
		return convertMD5(generateUUID());
	}

}
