package Cosm;

import org.json.JSONException;
import org.json.JSONObject;

public class Datapoint {
	private String at;
	private String value;
	public String getAt() {
		return this.at;
	}
	public void setAt(String at) {
		this.at = at;
	}
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("value",this.value);
		jo.put("at", this.at);
		return jo;
	}
	
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Datapoint [at=");
        builder.append(this.at);
        builder.append(", value=");
        builder.append(this.value);
        builder.append("]");
        return builder.toString();
    }
	
}
