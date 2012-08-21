package Cosm;


import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Feed {
	protected String description;
	protected String feed;
	protected Integer id;
	protected Location location;
	protected String title;
	private String errors;
	private String updated;
	private String creator; //
	private Status status;	
	private String website; //
	private String icon; //
	private String created;
	private String[] tags; //
	private String version; //
	private Boolean privateFeed; //
	private User user;
	private Datastream[] datastreams; //
	private String email;

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Feed [description=");
        builder.append(this.description);
        builder.append(", feed=");
        builder.append(this.feed);
        builder.append(", id=");
        builder.append(this.id);
        builder.append(", location=");
        builder.append(this.location);
        builder.append(", title=");
        builder.append(this.title);
        builder.append(", errors=");
        builder.append(this.errors);
        builder.append(", updated=");
        builder.append(this.updated);
        builder.append(", creator=");
        builder.append(this.creator);
        builder.append(", status=");
        builder.append(this.status);
        builder.append(", website=");
        builder.append(this.website);
        builder.append(", icon=");
        builder.append(this.icon);
        builder.append(", created=");
        builder.append(this.created);
        builder.append(", tags=");
        builder.append(Arrays.toString(this.tags));
        builder.append(", version=");
        builder.append(this.version);
        builder.append(", privateFeed=");
        builder.append(this.privateFeed);
        builder.append(", user=");
        builder.append(this.user);
        builder.append(", datastreams=");
        builder.append(Arrays.toString(this.datastreams));
        builder.append(", email=");
        builder.append(this.email);
        builder.append("]");
        return builder.toString();
    }
	
	
	
	public String getEmail() {
		return this.email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeed() {
		return this.feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getPrivateFeed() {
		return this.privateFeed;
	}

	public void setPrivateFeed(Boolean privateFeed) {
		this.privateFeed = privateFeed;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Feed() {
		this.version = Cosm.VERSION;
		this.tags = new String[0];
		this.datastreams = new Datastream[0];
	}
	
	
	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	
	public String[] getTags() {
		return this.tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Datastream[] getDatastreams() {
		return this.datastreams;
	}

	public void setDatastreams(Datastream[] datastreams) {
		this.datastreams = datastreams;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdated() {
		return this.updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean isPrivateFeed() {
		return this.privateFeed;
	}

	public void setPrivate(Boolean privateFeed) {
		this.privateFeed = privateFeed;
	}

	public String getErrors() {
		return this.errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
	
		jo.putOpt("description", this.description);
		jo.putOpt("feed", this.feed);
		//jo.putOpt("id", id);
		if ( this.location != null ) {
			jo.put("location", this.location.toJSONObject());
		}
	
		if ( this.title != null ) {
			jo.put("title",this.title);
		} else {
			throw new JSONException("required element title is missing from feed");
		}
	
		if ( this.version != null ) {
			jo.put("version", this.version);
		} else {
			throw new JSONException("required element version is missing in feed object");
		}
		
		jo.putOpt("creator", this.creator);
		jo.putOpt("website", this.website);
		jo.putOpt("icon", this.icon);
		JSONArray ja = new JSONArray();
		for(int i=0;(i<this.tags.length);i++) {
			ja.put(this.tags[i]);
		}
		jo.put("tags",ja);
		JSONArray jda = new JSONArray();
		for(int j=0;(j<this.datastreams.length);j++) {
			jda.put(this.datastreams[j].toJSONObject());
		}
		jo.put("datastreams", jda); //
		jo.putOpt("private", this.privateFeed); 
		jo.putOpt("email", this.email);
		return jo;
	}
	
}