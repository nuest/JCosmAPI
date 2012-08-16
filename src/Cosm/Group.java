package Cosm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Group {
	private String groupid;
	private String label;
	private String[] members;
	private Integer[] feeds;
	private String owner;
	
	@Override
	public String toString() {
		return this.label;
	}
	
	public Group() {
		this.owner = "";
		this.groupid = "";
		this.label = "";
		this.members = new String[0];
		this.feeds = new Integer[0];
	}
	
	
	
	public String getOwner() {
		return this.owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
	}



	public String getGroupid() {
		return this.groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getLabel() {
		return this.label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String[] getMembers() {
		return this.members;
	}
	public void setMembers(String[] members) {
		this.members = members;
	}
	public Integer[] getFeeds() {
		return this.feeds;
	}
	public void setFeeds(Integer[] feeds) {
		this.feeds = feeds;
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		
		jo.put("label", this.label);
		JSONArray ja = new JSONArray();
		for(int i=0;(i<this.members.length);i++) {
			ja.put(this.members[i]);
		}
		jo.put("members",ja);
		
		JSONArray jf = new JSONArray();
		for(int j=0;(j<this.feeds.length);j++) {
			jf.put(this.feeds[j]);
		}
		jo.put("feeds", jf);
		
				
		return jo;
	}
	
}
