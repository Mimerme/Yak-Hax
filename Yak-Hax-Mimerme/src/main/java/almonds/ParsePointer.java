package almonds;

import org.json.JSONException;
import org.json.JSONObject;

public class ParsePointer extends JSONObject
{
	public ParsePointer(String className, String objectId)
	{
		try
		{
			this.put("__type", "Pointer");
			this.put("className", className);
			this.put("objectId", objectId);
		}
		catch (JSONException e)
		{

		}
	}
}
