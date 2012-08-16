package Cosm;

import org.json.JSONException;
import org.json.JSONObject;

public class Resource {
	private String feedId;
	private String datastreamId;
	
	public String getFeedId() {
		return this.feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public String getDatastreamId() {
		return this.datastreamId;
	}
	public void setDatastreamId(String datastreamId) {
		this.datastreamId = datastreamId;
	}	
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		
		if (( this.datastreamId != null ) && (this.feedId == null )) {
			throw new JSONException("feedid is not set while datastreamid is set");
		}
		
		jo.putOpt("feed_id",this.feedId);
		jo.putOpt("datastream_id",this.datastreamId);
		
		return jo;
	}
	
}
