package Yak_Hax.Yak_Hax_Mimerme;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class Main {

	static final String BASE_URL = "https://us-central-api.yikyakapi.net";
	//use the dns found IP to prevent DNS lookup errors (it happens on my network ;_;)
	static final String BASE_ENCODER_URL = "https://yakhax-encoder.herokuapp.com/?message=";

	static final String YIKYAK_VERSION = "2.8.1";
	static final String API_VERSION = "0.7a";
	
	static final boolean PRELOAD_CONFIG = true;

	static Scanner input = new Scanner(System.in);

	//https://us-central-api.yikyakapi.net/api
	//endpoint

	public static void main(String[] args) throws IOException {
		System.out.println("Util API debugger " + API_VERSION);
		if(PRELOAD_CONFIG){
			System.out.println(System.getenv("UTILS_PATH") + "\\yhack\\values.txt");
			FileInputStream fs= new FileInputStream(System.getenv("UTILS_PATH") + "\\yhack\\values.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			YikYakProfile.TOKEN = br.readLine();
			YikYakProfile.USER_ID = br.readLine();
			System.out.println("Setting credentials as");
			System.out.println(YikYakProfile.TOKEN + " : " + YikYakProfile.USER_ID);
			System.out.println("==============================================================");
		}


		ArrayList<String> parameters = new ArrayList<String>();

		if(YikYakProfile.TOKEN == null || YikYakProfile.USER_ID == null){
			System.out.println("[ERROR!] UserId / Token remains unset");
			System.exit(-1);
		}

		if(args.length == 0){
			System.out.println("UtilConsole YikYak API Debugger");
			System.out.println("Version " + Main.API_VERSION);
			System.exit(0);
		}
		
		switch(args[0]){
		case "get-messages":
			if(args.length == 2 && args[1].equals("default")){
				System.out.println("Running \'Get Message\' default build");
				parameters.add("30.0");
				parameters.add("0");
				parameters.add("40.5647994");
				parameters.add("-74.3561006");
			}

			else{
				System.out.println("Starting \'Get Message\' request build");

				//Parse accuracy
				System.out.print("Enter accuracy (default 30.0): ");
				parameters.add(input.next());

				//Parse basecamp
				System.out.print("Enter basecamp (default 0): ");
				parameters.add(String.valueOf(input.nextInt()));

				//Parse lat
				System.out.print("Enter lat (recomended 40.5647994): ");
				parameters.add(input.next());

				//Parse long
				System.out.print("Enter long (recomended -74.3561006): ");
				parameters.add(input.next());
			}
			System.out.println(getMessages(parameters).text());
			System.exit(0);
			break;

		default:
			System.out.println("Unrecognized parameter \'" + args[0] + "\'" );
			System.exit(0);
			break;

		}
	}

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
