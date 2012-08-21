package Cosm;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Apikey {
	private String label;
	private Boolean privateAccess;
	private Permission[] permissions;
	private String expiresAt;
	private String apikey;
	
	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getExpiresAt() {
		return this.expiresAt;
	}

	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Apikey() {
		this.permissions = new Permission[0];
	}
	
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Boolean getPrivateAccess() {
		return this.privateAccess;
	}
	public void setPrivateAccess(Boolean privateAccess) {
		this.privateAccess = privateAccess;
	}
	public Permission[] getPermissions() {
		return this.permissions;
	}
	public void setPermissions(Permission[] permissions) {
		this.permissions = permissions;
	}
	
	JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		
		if ( this.label != null ) {
			jo.put("label",this.label);
		} else {
			throw new JSONException("required element label is null ");
		}
		
		if ( this.expiresAt != null ) {
			jo.put("expiresAt",this.expiresAt);
		}
		
		if ( this.privateAccess != null ) {
			jo.put("private_access", this.privateAccess.toString());
		}
		
		JSONArray ja = new JSONArray();
		for(int i=0;(i<this.permissions.length);i++) {
			ja.put(this.permissions[i].toJSONObject());
		}
		jo.put("permissions", ja);
		
		
		return jo;
	}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Apikey [label=");
        builder.append(this.label);
        builder.append(", privateAccess=");
        builder.append(this.privateAccess);
        builder.append(", permissions=");
        builder.append(Arrays.toString(this.permissions));
        builder.append(", expiresAt=");
        builder.append(this.expiresAt);
        builder.append(", apikey=");
        builder.append(this.apikey);
        builder.append("]");
        return builder.toString();
    }
	
	
}
