package almonds;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The ParseQuery class defines a query that is used to fetch ParseObjects. The
 * most common use case is finding all objects that match a query through the
 * findInBackground method, using a FindCallback.
 * 
 * @author js
 */
public class ParseQuery
{
	private String mClassName; // the name of the Parse class to perform the
								// query on
	private SimpleEntry mWhereEqualTo = null; // tracks "WHERE" constraints
	private List<String> mOrder = new ArrayList<String>(); // tracks keys to
															// sort by

	private int mLimit = -1;
	private int mSkip = 0;

	// query = key: [constraints]
	// constraint = optional code : [value]

	// where = key, constraint code ("gte", "lte")
	// constraint code = {

	/**
	 * Constructs a query. A default query with no further parameters will
	 * retrieve all ParseObjects of the provided class.
	 * 
	 * @param className
	 *            The name of the class to retrieve ParseObjects for.
	 */
	public ParseQuery(String className)
	{
		mClassName = className;
	}

	/**
	 * Helper Thread to execute Parse Get calls off of the main application
	 * thread.
	 * 
	 */
	class GetInBackgroundThread extends Thread
	{
		GetCallback mGetCallback;
		String mObjectId;

		/**
		 * @param objectId
		 * @param callback
		 */
		GetInBackgroundThread(String objectId, GetCallback callback)
		{
			mGetCallback = callback;
			mObjectId = objectId;
		}

		public void run()
		{
			try
			{
				ParseObject getObject = get(mObjectId);
				mGetCallback.done(getObject);
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Constructs a ParseObject whose id is already known by fetching data from
	 * the server in a background thread. This does not use caching. This is
	 * preferable to using the ParseObject(className, objectId) constructor,
	 * unless your code is already running in a background thread.
	 * 
	 * @param objectId
	 *            Object id of the ParseObject to fetch.
	 * @param callback
	 *            callback.done(object, e) will be called when the fetch
	 *            completes.
	 */
	public void getInBackground(String objectId, GetCallback callback)
	{
		GetInBackgroundThread t = new GetInBackgroundThread(objectId, callback);
		t.start();
	}

	/**
	 * Constructs a ParseObject whose id is already known by fetching data from
	 * the server. This does not use caching.
	 * 
	 * @param theObjectId
	 *            Object id of the ParseObject to fetch.
	 * @return
	 * @throws ParseException
	 *             Throws an exception when there is no such object or when the
	 *             network connection fails.
	 */
	public ParseObject get(String theObjectId) throws ParseException
	{
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(Parse.getParseAPIUrlClasses() + mClassName + "/"
					+ theObjectId);
			httpget.addHeader("X-Parse-Application-Id", Parse.getApplicationId());
			httpget.addHeader("X-Parse-REST-API-Key", Parse.getRestAPIKey());

			HttpResponse httpResponse = httpclient.execute(httpget);

			ParseResponse response = new ParseResponse(httpResponse);

			if (!response.isFailed())
			{
				return new ParseObject(mClassName, response.getJsonObject());
			}
			else
			{
				throw response.getException();
			}
		}
		catch (ClientProtocolException e)
		{
			throw ParseResponse.getConnectionFailedException(e.getMessage());
		}
		catch (IOException e)
		{
			throw ParseResponse.getConnectionFailedException(e.getMessage());
		}
	}

	/**
	 * A thread used to execute a ParseQuery find off of the main thread.
	 * 
	 */
	private class FindInBackgroundThread extends Thread
	{
		FindCallback mFindCallback;

		FindInBackgroundThread(FindCallback callback)
		{
			mFindCallback = callback;
		}

		public void run()
		{
			List<ParseObject> objects = null;
			ParseException exception = null;
			
			try
			{
				objects = find();
			}
			catch (ParseException e)
			{
				exception = e;
			}
			
			mFindCallback.done (objects, exception);
		}
	}

	/**
	 * Retrieves a list of ParseObjects that satisfy this query from the server
	 * in a background thread. This is preferable to using find(), unless your
	 * code is already running in a background thread.
	 * 
	 * @param callback
	 *            callback - callback.done(object, e) is called when the find
	 *            completes.
	 */
	public void findInBackground(FindCallback callback)
	{
		FindInBackgroundThread t = new FindInBackgroundThread(callback);
		t.start();
	}

	/**
	 * Retrieves a list of ParseObjects that satisfy this query. Uses the
	 * network and/or the cache, depending on the cache policy.
	 * 
	 * @return A list of all ParseObjects obeying the conditions set in this
	 *         query.
	 */
	public List<ParseObject> find() throws ParseException
	{
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(Parse.getParseAPIUrlClasses() + mClassName
					+ getURLConstraints());
			httpget.addHeader("X-Parse-Application-Id", Parse.getApplicationId());
			httpget.addHeader("X-Parse-REST-API-Key", Parse.getRestAPIKey());

			HttpResponse httpResponse = httpclient.execute(httpget);
			ParseResponse parseResponse = new ParseResponse(httpResponse);

			if (parseResponse.isFailed())
			{
				throw parseResponse.getException();
			}

			JSONObject obj = parseResponse.getJsonObject();

			if (obj == null)
			{
				throw parseResponse.getException();
			}

			try
			{
				ArrayList<ParseObject> objects = new ArrayList<ParseObject>();
				JSONArray results = obj.getJSONArray("results");

				for (int i = 0; i < results.length(); i++)
				{
					ParseObject parseObject = new ParseObject(mClassName);
					JSONObject jsonObject = results.getJSONObject(i);

					for (String name : JSONObject.getNames(jsonObject))
					{
						parseObject.put(name, jsonObject.get(name));
					}

					objects.add(parseObject);

				}

				return objects;
			}
			catch (JSONException e)
			{
				throw new ParseException(ParseException.INVALID_JSON,
						"Error parsing the array of results returned by query.", e);
			}
		}
		catch (ClientProtocolException e)
		{
			throw ParseResponse.getConnectionFailedException(e);
		}
		catch (IOException e)
		{
			throw ParseResponse.getConnectionFailedException(e);
		}
	}

	/**
	 * Add a constraint to the query that requires a particular key's value to
	 * be equal to the provided value.
	 * 
	 * @param key
	 *            The key to check.
	 * @param value
	 *            The value that the ParseObject must contain.
	 * @return Returns the query, so you can chain this call.
	 */
	public ParseQuery whereEqualTo(String key, Object value)
	{
		mWhereEqualTo = new SimpleEntry<String, Object>(key, value);
		return this;
	}

	/**
	 * Add a constraint to the query that requires a particular key's value to
	 * be greater than the provided value.
	 * 
	 * @param key
	 *            The key to check.
	 * @param value
	 *            The value that provides an lower bound.
	 * @return Returns the query, so you can chain this call.
	 */
	public ParseQuery whereGreaterThan(String key, Object value)
	{
		// MultiValueMap map = new MultiValueMap();
		return this;
	}

	/**
	 * Helper to easily decide if any where or order constraints have been set.
	 * 
	 * @return True if any type of constraints have been placed on the Query
	 */
	private boolean hasConstraints()
	{
		return hasWhereConstraints() || hasOrderConstraints() || hasLimitConstraints()
				|| hasSkipConstraints();
	}

	private boolean hasLimitConstraints()
	{
		return mLimit >= 0;
	}

	private boolean hasWhereConstraints()
	{
		return mWhereEqualTo != null;
	}

	private boolean hasOrderConstraints()
	{
		return !mOrder.isEmpty();
	}

	private boolean hasSkipConstraints()
	{
		return mSkip > 0;
	}

	/**
	 * Constraints on a Query using the REST API are communicated as 'where' and
	 * 'order', 'limit' and 'skip' parameters in the URL. This method takes the
	 * current constraints on the Query and returns them formatted as a partial
	 * URL.
	 * 
	 * @return The URL formatted Query constraints.
	 */
	private String getURLConstraints()
	{
		String url = "";
		Boolean firstParam = true;

		try
		{

			if (hasConstraints())
			{
				url = "?";

				if (hasWhereConstraints())
				{
					if (!firstParam)
					{
						url += "&";
					}
					else
					{
						firstParam = false;
					}

					url += "where=" + URLEncoder.encode(getJSONWhereConstraints(), "UTF-8");
				}

				if (hasOrderConstraints())
				{
					if (!firstParam)
					{
						url += "&";
					}
					else
					{
						firstParam = false;
					}

					url += "order=";

					String orderParams = "";

					for (Iterator<String> i = mOrder.iterator(); i.hasNext();)
					{
						orderParams += i.next();

						if (i.hasNext())
						{
							orderParams += ",";
						}
					}

					url += URLEncoder.encode(orderParams, "UTF-8");
				}

				if (hasLimitConstraints())
				{
					if (!firstParam)
					{
						url += "&";
					}
					else
					{
						firstParam = false;
					}

					url += URLEncoder.encode("limit=" + mLimit, "UTF-8");
				}

				if (hasSkipConstraints())
				{
					if (!firstParam)
					{
						url += "&";
					}
					else
					{
						firstParam = false;
					}

					url += URLEncoder.encode("skip=" + mSkip, "UTF-8");
				}
			}
		}
		catch (UnsupportedEncodingException e)
		{
			url = "";
		}

		return url;
	}

	/**
	 * Creates a Parse readable JSON string containing the current Query where
	 * constraints. This can then be URL formatted for using in an HTTP request.
	 * 
	 * @return A Parse readable JSON string of constraints to place on a Query.
	 */
	private String getJSONWhereConstraints()
	{
		String js = "";

		if (mWhereEqualTo != null)
		{
			JSONObject jo = new JSONObject();

			try
			{
				jo.put(((String) mWhereEqualTo.getKey()), mWhereEqualTo.getValue());
				js = jo.toString();
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

		return js;
	}

	/**
	 * Sorts the results in descending order by the given key.
	 * 
	 * @param key
	 *            The key to order by.
	 * @return Returns the query, so you can chain this call.
	 */
	public ParseQuery orderByDescending(String key)
	{
		mOrder.add("-" + key);
		return this;
	}

	/**
	 * Also sorts the results in descending order by the given key. The previous
	 * sort keys have precedence over this key.
	 * 
	 * @param key
	 *            The key to order by.
	 * @return Returns the query so you can chain this call.
	 */
	public ParseQuery addDescendingOrder(String key)
	{
		return orderByDescending(key);
	}

	/**
	 * Sorts the results in ascending order by the given key.
	 * 
	 * @param key
	 *            The key to order by.
	 * @return Returns the query, so you can chain this call.
	 */
	public ParseQuery orderByAscending(String key)
	{
		mOrder.add(key);
		return this;
	}

	/**
	 * Also sorts the results in ascending order by the given key. The previous
	 * sort keys have precedence over this key.
	 * 
	 * @param key
	 *            The key to order by.
	 * @return Returns the query so you can chain this call.
	 */
	public ParseQuery addAscendingOrder(String key)
	{
		return orderByAscending(key);
	}

	/**
	 * Controls the maximum number of results that are returned. Setting a
	 * negative limit denotes retrieval without a limit.
	 * 
	 * @param newLimit
	 */
	public void setLimit(int newLimit)
	{
		mLimit = newLimit;
	}

	/**
	 * Accessor for the limit. Defaults to -1 which instructs a retrieval
	 * without a limit.
	 * 
	 * @return
	 */
	public int getLimit()
	{
		return mLimit;
	}

	/**
	 * Controls the number of results to skip before returning any results. This
	 * is useful for pagination. Default is to skip zero results.
	 * 
	 * @param newSkip
	 */
	public void setSkip(int newSkip)
	{
		if (newSkip < 0)
		{
			return;
		}

		mSkip = newSkip;
	}

	/**
	 * Accessor for the skip value.
	 * 
	 * @return
	 */
	public int getSkip()
	{
		return mSkip;
	}
}
