package Cosm;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Permission {
	private String sourceIp;
	private String referer;
	private String minimumInterval;
	private String label;
	private Resource[] resources;
	private AccessMethod[] accessMethods;

	public Permission() {
		this.resources = new Resource[0];
		this.accessMethods = new AccessMethod[0];
	}
	
	public String getSourceIp() {
		return this.sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public String getReferer() {
		return this.referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getMinimumInterval() {
		return this.minimumInterval;
	}
	public void setMinimumInterval(String minimumInterval) {
		this.minimumInterval = minimumInterval;
	}
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Resource[] getResources() {
		return this.resources;
	}
	public void setResources(Resource[] resources) {
		this.resources = resources;
	}
	public AccessMethod[] getAccessMethods() {
		return this.accessMethods;
	}
	public void setAccessMethods(AccessMethod[] accessMethods) {
		this.accessMethods = accessMethods;
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		
		jo.putOpt("source_ip", this.sourceIp);
		jo.putOpt("referer", this.referer);
		jo.putOpt("minimum_interval", this.minimumInterval);
		jo.putOpt("label", this.label);
		
		{
			JSONArray ja = new JSONArray();
			for(int i=0;(i<this.resources.length);i++) {
				ja.put(this.resources[i].toJSONObject());
			}
			jo.put("resources", ja);
		}
		
		{
			JSONArray ja = new JSONArray();
			for(int i=0;(i<this.accessMethods.length);i++) {
				ja.put(this.accessMethods[i].toString());
			}
			jo.put("access_methods", ja);
		}
	
		return jo;
	}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Permission [sourceIp=");
        builder.append(this.sourceIp);
        builder.append(", referer=");
        builder.append(this.referer);
        builder.append(", minimumInterval=");
        builder.append(this.minimumInterval);
        builder.append(", label=");
        builder.append(this.label);
        builder.append(", resources=");
        builder.append(Arrays.toString(this.resources));
        builder.append(", accessMethods=");
        builder.append(Arrays.toString(this.accessMethods));
        builder.append("]");
        return builder.toString();
    }
	
}
