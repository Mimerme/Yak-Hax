package Yak_Hax.Yak_Hax_Mimerme;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class Main {

	static final boolean PRELOAD_CONFIG = true;

	static Scanner input = new Scanner(System.in);
	//TODO: Convert all ArrayList Parameters to HashMaps
	
	//https://us-central-api.yikyakapi.net/api
	//endpoint

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		System.out.println(YikYakAPI.registerNewUser());
/*		System.out.println(YikYakAPI.startVerifyAccount(YikYakProfile.TOKEN, "(732) 362-4979", "USA", "+1"));
*//*		System.out.println(YikYakAPI.verifyAccount(YikYakProfile.USER_ID, YikYakProfile.TOKEN,"833brjut2x1q3cecdmrqgkuz88", "5284"));
*//*		YikYakAPI.verifyAccount(YikYakProfile.USER_ID, YikYakProfile.TOKEN, "(908) 444-6806");
		System.out.println("Util API debugger " + YikYakAPI.getAPIVersion());
		System.out.println("This branch of the API requires the jUtilConsole, check the GitHub repository for more details");
		if(PRELOAD_CONFIG){
			System.out.println(System.getenv("UTILS_PATH") + "\\yhack\\values.txt");
			FileInputStream fs= new FileInputStream(System.getenv("UTILS_PATH") + "\\yhack\\values.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			YikYakAPI.login(br.readLine(), br.readLine());
			System.out.println("Setting credentials as");
			System.out.println(YikYakProfile.TOKEN + " : " + YikYakProfile.USER_ID);
			System.out.println("==============================================================");
		}


		ArrayList<String> parameters = new ArrayList<String>();
		Map<String, String> postParameters = new HashMap<String, String>();

		if(YikYakProfile.TOKEN == null || YikYakProfile.USER_ID == null){
			System.out.println("[ERROR!] UserId / Token remains unset");
			System.exit(-1);
		}

		if(args.length == 0){
			System.out.println("UtilConsole YikYak API Debugger");
			System.out.println("Version " + YikYakAPI.getAPIVersion());
			System.exit(0);
		}

		parameters.add("48.0");
		parameters.add("0");
		parameters.add("40.5647994");
		parameters.add("-74.3561006");
		parameters.add("R/556616c60b1cef81f019723059154");
		parameters.add("R/556616c60b1cef81f019723059154");
		
		SortedMap<String, String> getparameters = new TreeMap<String, String>();
		getparameters.put("accuracy", "48.0");
		getparameters.put("bc", "0");
		getparameters.put("long", "-74.3561006");
		getparameters.put("lat", "40.5647994");
		getparameters.put("userLat", "40.5647994");
		getparameters.put("userLong", "-74.3561006");
		getparameters.put("userID", YikYakProfile.USER_ID);
		getparameters.put("token", YikYakProfile.TOKEN);
		getparameters.put("version", YikYakAPI.YIKYAK_VERSION);

		postParameters.put("bc", "0");
		postParameters.put("bypassedThreatPopup", "0");
		postParameters.put("lat", "40.5647994");
		postParameters.put("userLat", "40.5647994");
		postParameters.put("userLong", "-74.3561006");
		postParameters.put("message", "I feel bored");
		postParameters.put("comment", "I feel bored");
		postParameters.put("messageID", "R/556616c60b1cef81f019723059154");
		postParameters.put("token", YikYakProfile.TOKEN);
		postParameters.put("userID", YikYakProfile.USER_ID);
		postParameters.put("version", YikYakAPI.YIKYAK_VERSION);

		switch(args[0]){
		case "test-md5":
			System.out.println(APIUtils.convertMD5(args[1]));
			break;
		case "new-user":
			YikYakAPI.registerNewUser(parameters);
			break;
		case "get-messages":
			if(args.length == 2 && args[1].equals("default"))
				System.out.println("Running \'Get Messages\' default build");
			else{
				System.out.println("Starting \'Get Comments\' request build");
				parameters.clear();
				//Parse accuracy
				System.out.println("Enter accuracy (default 30.0): ");
				parameters.add(input.next());

				//Parse basecamp
				System.out.println("Enter basecamp (default 0): ");
				parameters.add(String.valueOf(input.nextInt()));

				//Parse lat
				System.out.println("Enter lat (recomended 40.5647994): ");
				parameters.add(input.next());

				//Parse long
				System.out.println("Enter long (recomended -74.3561006): ");
				parameters.add(input.next());
			}
			System.out.println(YikYakAPI.getYaks(getparameters).text());
			System.exit(0);
			break;
		case "get-mytops":
			System.out.println("Running \'Get MyTops\' default build");
			System.out.println(YikYakAPI.getMyTops(parameters).text());
			System.exit(0);
			break;
		case "test-hash":
			System.out.println("Enter decoded request:");
			System.out.println(YikYakAPI.getHash(input.next()));
			break;
		case "post-yak":
			if(args.length == 2 && args[1].equals("default"))
				System.out.println("Running \'Post Yak\' default build");
			else{
				System.out.println("Running \'Post Yak\' request build");
				postParameters.clear();
				
				//Parse basecamp
				System.out.println("Enter basecamp (default 0): ");
				postParameters.put("bc", String.valueOf(input.nextInt()));

				//Parse bypassedThreatPopup
				System.out.println("Enter bypassedThreatPopup (default 0): ");
				postParameters.put("bypassedThreatPopup", String.valueOf(input.nextInt()));

				//Parse lat
				System.out.println("Enter lat (recomended 40.5647994): ");
				postParameters.put("lat", input.next());

				//Parse long
				System.out.println("Enter long (recomended -74.3561006): ");
				postParameters.put("long", input.next());
				
				//Parse message
				System.out.println("Enter message (default\'I am bored\'): ");
				postParameters.put("message", input.next());
				
				postParameters.put("token", YikYakProfile.TOKEN);
				postParameters.put("userID", YikYakProfile.USER_ID);
				postParameters.put("version", YikYakAPI.YIKYAK_VERSION);
			}
			System.out.println(YikYakAPI.postYak(postParameters).text());
			System.exit(0);
			break;
		case "get-areatop":
			System.out.println("Running \'Get AreaTop\' default build");
			System.out.println(YikYakAPI.getAreaTop(parameters).text());
			System.exit(0);
			break;
		case "get-areahot":
			System.out.println("Running \'Get AreaHot\' default build");
			System.out.println(YikYakAPI.getAreaHot(parameters).text());
			System.exit(0);
			break;
		case "gen-useragent":
			System.out.println("Generating random User Agent");
			System.out.println(APIUtils.generateRandomUserAgent());
			System.exit(0);
			break;
		case "get-comments":
			if(args.length == 2 && args[1].equals("default"))
				System.out.println("Running \'Get Comments\' default build");
			else{
				System.out.println("Starting \'Get Comments\' request build");
				parameters.clear();

				//Parse accuracy
				System.out.println("Enter accuracy (default 30.0): ");
				parameters.add(input.next());

				//Parse basecamp
				System.out.println("Enter basecamp (default 0): ");
				parameters.add(String.valueOf(input.nextInt()));

				//Parse lat
				System.out.println("Enter lat (recomended 40.5647994): ");
				parameters.add(input.next());

				//Parse long
				System.out.println("Enter long (recomended -74.3561006): ");
				parameters.add(input.next());

				//Parse messageID
				System.out.println("Enter messageID (recomended R/556616c60b1cef81f019723059154): ");
				parameters.add(input.next());
			}
			System.out.println(YikYakAPI.getYakComments(parameters).text());
			System.exit(0);
			break;

		default:
			System.out.println("Unrecognized parameter \'" + args[0] + "\'" );
			System.exit(0);
			break;

		}*/
	}
}
