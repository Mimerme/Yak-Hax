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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

//had to do this manually cause JSoup doesn't support raw bodies in POST requests -_-
//Really only used for JSON bodies
public class PostRequest {
	public static String PostBodyRequest(String URL, String JSONRaw, String UserAgent) throws IOException{
		String type = "application/json";
		URL u = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty( "Content-Type", type );
		conn.setRequestProperty( "User-Agent", UserAgent);
		OutputStream os = conn.getOutputStream();
		os.write(JSONRaw.getBytes());
		os.flush();
		os.close();

		String response = null;
		DataInputStream input = new DataInputStream (conn.getInputStream());
		while (null != ((response = input.readLine()))) {
			input.close ();
			return response;
		}
		return null;
	}
	
	public static String PostBodyRequest(String URL, String JSONRaw, TreeMap<String, String> headers) throws IOException{
		
		HttpPost post = new HttpPost(URL);
		
		HttpClient httpclient = HttpClients.custom()
			    .build();
		post.setEntity(new StringEntity(JSONRaw));
		post.setHeader("Content-Type", "application/json; charset=utf-8");
		for(Map.Entry<String,String> entry : headers.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			post.setHeader(key, value);
		}
		HttpResponse response = httpclient.execute(post);
		
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
}
