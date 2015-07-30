package Yak_Hax.Yak_Hax_Mimerme;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

//had to do this manually cause JSoup doesn't support raw bodies in POST requests -_-
//Really only used for JSON bodies and phone verification
public class PostRequest {
	public static String PostBodyRequest(String URL, String JSONRaw, String UserAgent) throws IOException{
		String type = "application/json";
		URL u = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty( "Accept-Encoding", "gzip" );
		conn.setRequestProperty( "Connection", "Keep-Alive" );
		conn.setRequestProperty( "User-Agent", UserAgent );
		OutputStream os = conn.getOutputStream();
		os.write(JSONRaw.getBytes());
		os.flush();
		os.close();

		String response = null;
		DataInputStream input = new DataInputStream (conn.getInputStream());
		while (null != ((response = input.readLine()))) {
			input.close ();
			break;
		}
		return response;
	}
}
