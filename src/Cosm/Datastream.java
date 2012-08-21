package Cosm;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Datastream {
	private String current_value;
	private String id;
	private String max_value;
	private String min_value;
	private String[] tags;
	private Unit unit;
	private String at;
	
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Datastream [current_value=");
        builder.append(this.current_value);
        builder.append(", id=");
        builder.append(this.id);
        builder.append(", max_value=");
        builder.append(this.max_value);
        builder.append(", min_value=");
        builder.append(this.min_value);
        builder.append(", tags=");
        builder.append(Arrays.toString(this.tags));
        builder.append(", unit=");
        builder.append(this.unit);
        builder.append(", at=");
        builder.append(this.at);
        builder.append("]");
        return builder.toString();
    }
	
	public Datastream() {
		this.tags = new String[0];
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		
		jo.put("current_value", this.current_value);
		jo.put("id", this.id);
		jo.putOpt("max_value", this.max_value);
		jo.putOpt("min_value", this.min_value);
		JSONArray ja = new JSONArray();
		for(int i=0;(i<this.tags.length);i++) {
			ja.put(this.tags[i]);
		}
		jo.putOpt("tags",ja);
		if ( this.unit != null ) {
			jo.put("unit", this.unit.toJSONObject());
		}
		return jo;
	}
	
	
	public String getAt() {
		return this.at;
	}
	
	public void setAt(String at) {
		this.at = at;
	}
	
	public String getCurrentValue() {
		return this.current_value;
	}

	public void setCurrentValue(String current_value) {
		this.current_value = current_value;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaxValue() {
		return this.max_value;
	}

	public void setMaxValue(String max_value) {
		this.max_value = max_value;
	}

	public String getMinValue() {
		return this.min_value;
	}

	public void setMinValue(String min_value) {
		this.min_value = min_value;
	}

	public String[] getTags() {
		return this.tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}	
}
