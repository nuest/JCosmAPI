package Cosm;

import org.json.JSONException;
import org.json.JSONObject;


public class Trigger {
	private String thresholdValue;
	private String user;
	private String notifiedAt;
	private String url;
	private TriggerType type;
	private String id;
	private Integer environment_id;
	private String stream_id;
		
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		
		
		jo.putOpt("threshold_value", this.thresholdValue);
		jo.putOpt("url", this.url);
		if ( this.type != null ) {
			jo.putOpt("trigger_type", this.type.toString());
		}
		jo.putOpt("environment_id", this.environment_id);
		jo.putOpt("stream_id", this.stream_id);
		jo.putOpt("user", this.user);
		jo.putOpt("notified_at",this.notifiedAt);
		jo.putOpt("id", this.id);
		
		return jo;
	}
	
	
	
	public String getUser() {
		return this.user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	public String getNotifiedAt() {
		return this.notifiedAt;
	}



	public void setNotifiedAt(String notifiedAt) {
		this.notifiedAt = notifiedAt;
	}



	public Integer getEnvironmentId() {
		return this.environment_id;
	}

	public void setEnvironmentId(Integer environment_id) {
		this.environment_id = environment_id;
	}

	public String getStreamId() {
		return this.stream_id;
	}

	public void setStreamId(String stream_id) {
		this.stream_id = stream_id;
	}

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getThresholdValue() {
		return this.thresholdValue;
	}
	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	public TriggerType getType() {
		return this.type;
	}
	public void setType(TriggerType type) {
		this.type = type;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
}