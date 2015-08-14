package almonds;

import java.io.IOException;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

class ParseResponse
{
	HttpResponse mHttpResponse;

	static final String RESPONSE_CODE_JSON_KEY = "code";
	static final String RESPONSE_ERROR_JSON_KEY = "error";

	public ParseResponse(HttpResponse httpresponse)
	{
		mHttpResponse = httpresponse;
	}

	public JSONObject getJsonObject()
	{
		try
		{
			return new JSONObject(EntityUtils.toString(mHttpResponse.getEntity()));
		}
		catch (org.apache.http.ParseException e)
		{
			return null;
		}
		catch (JSONException e)
		{
			return null;
		}
		catch (IOException e)
		{
			return null;
		}
	}

	public boolean isFailed()
	{
		return hasConnectionFailed() || hasErrorCode();
	}

	public boolean hasConnectionFailed()
	{
		return mHttpResponse.getEntity() == null;
	}

	public boolean hasErrorCode()
	{
		int statusCode = mHttpResponse.getStatusLine().getStatusCode();
		
		return (statusCode < 200 || statusCode >= 300);
	}

	public ParseException getException()
	{
		if (hasConnectionFailed())
		{
			// connection failed situation

			return new ParseException(ParseException.CONNECTION_FAILED,
					"Connection to Parse servers failed.");
		}

		if (!hasErrorCode())
		{
			// there's no error code so we shouldn't be here
			return new ParseException(ParseException.OPERATION_FORBIDDEN,
					"getException called with successful response");
		}
		// cases going forward had a successful with Parse, but something
		// else went wrong, information about which is found in the
		// json-encoded http response

		JSONObject response = getJsonObject();

		if (response == null)
		{
			return new ParseException(ParseException.INVALID_JSON,
					"Invalid response from Parse servers.");
		}

		// we have a valid json response

		// first attempt to read the error code
		// this key doesn't exist when the supplied Parse keys are invalid

		int code;

		try
		{
			code = response.getInt(RESPONSE_CODE_JSON_KEY);
		}
		catch (JSONException e)
		{
			code = ParseException.NOT_INITIALIZED;
		}

		// read the error message

		String message;

		try
		{
			message = response.getString(RESPONSE_ERROR_JSON_KEY);
		}
		catch (JSONException e)
		{
			message = "Error undefinted by Parse server.";
		}

		// build and return an exception with the supplied code and error
		
		return new ParseException(code, message);

	}

	static ParseException getConnectionFailedException(String message)
	{
		return new ParseException(ParseException.CONNECTION_FAILED,
				"Connection failed with Parse servers.  Log: " + message);
	}
	
	static ParseException getConnectionFailedException(Throwable e)
	{
		return getConnectionFailedException(e.getMessage());
	}
}
