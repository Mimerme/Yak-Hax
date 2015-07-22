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

	static final boolean PRELOAD_CONFIG = true;
	static final String API_VERSION = "0.9a";

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

		parameters.add("30.0");
		parameters.add("0");
		parameters.add("40.5647994");
		parameters.add("-74.3561006");
		parameters.add("R/556616c60b1cef81f019723059154");

		switch(args[0]){
		case "get-messages":
			if(args.length == 2 && args[1].equals("default"))
				System.out.println("Running \'Get Messages\' default build");
			else{
				System.out.println("Starting \'Get Comments\' request build");

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
			System.out.println(YikYakAPI.getYaks(parameters).text());
			System.exit(0);
			break;
		case "get-mytops":
			System.out.println("Running \'Get MyTops\' default build");
			System.out.println(YikYakAPI.getMyTops(parameters).text());
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
				System.out.println("Running \'Get Message\' default build");
			else{
				System.out.println("Starting \'Get Comments\' request build");

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

		}
	}
}
