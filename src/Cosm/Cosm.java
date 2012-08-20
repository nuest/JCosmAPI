package Cosm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Cosm.Client.CosmClient;

/** The Cosm class provides the methods for interfacing with the Cosm (www.cosm.com) webservice.
 *
 * @author Pieter Meulenhoff
 * @version 15 June 2012
 */
public class Cosm {
	private static final String IO_EXCEPTION_MESSAGE = "Caught IO Exception. Operation failed.";
	private static final String URL_SYNTAX_EXCEPTION_MESSAGE = "Syntax of the URL is not correct. Operation failed.";
	private static final String NULL_POINTER_EXCEPTION_MESSAGE = "Caught Null pointer exception. Operation failed.";

	public static final String VERSION = "1.0.0";
	
    private static final String API_HOST = "api.cosm.com";
    private static final String API_SCHEME = "http";
    private static final String API_VERSION = "/v2/";

    public static final String API_BASE_URL_V2 = API_SCHEME+ "://" + API_HOST + API_VERSION; //"http://api.cosm.com/v2/";
    private static final String JSON_FILE_EXTENSION = ".json";
    private static final String HEADER_PARAM_LOCATION = "Location";
    private static final String API_RESOURCE_FEEDS = "feeds";

	private CosmClient client;

	/** Constructor based on access with an Cosm API key
	 * 
	 * @param apikey Cosm API key
	 */
	public Cosm(String apikey) {
		this.client = new CosmClient(apikey);
	}

	/** Constructor based on access with a username and password
	 * 
	 * @param username Cosm username (or login)
	 * @param password Cosm password
	 */
	public Cosm(String username,String password) {
		this.client = new CosmClient(username,password);
	}


	/** Retrieve a Feed object from Cosm 
	 * 
	 * retrieves a Feed object by its identifier. If no feed was found or a problem occurs, a CosmException is thrown. 
	 * 
	 * @param feedid identifier of the Cosm feed
	 * @return Feed object
	 * @throws CosmException
	 */
	public Feed getFeed(int feedid) throws CosmException {
		return getFeed(feedid,false);
	}

	/**
	 * Get a feed object by its feed identifier
	 * 
	 * @param feedid Id of the Cosm feed to retrieve
	 * @return Feed object which corresponds to the id provided as the parameter
	 * @throws CosmException If something goes wrong, or if the Feed was not found.
	 */
	public Feed getFeed(int feedid, boolean show_user) throws CosmException {
		try {			
			HttpGet hr = null;
			if ( show_user ) {
				hr = new HttpGet(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+JSON_FILE_EXTENSION+"?show_user=true");
			} else {
				hr = new HttpGet(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+JSON_FILE_EXTENSION+"?show_user=false");				
			}
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toFeed(body);				
			}
            throw new CosmException(statusLine,body);			
		} catch ( IOException e ) {
			throw new CosmException(IO_EXCEPTION_MESSAGE);
		}
	}
	
	/**
	 * 
	 */
	public Feed[] getFeeds(String query,Content content,String tag,String user,String units,Status status,Order order,Boolean show_user) throws CosmException {
		return getFeeds(query,content,tag,user,units,status,order,show_user,null,null,null,null);
	}

	// get feeds with more search options
	/**
	 * returns a list of feed objects based on a number of optional query parameters. If set to {@link null}, a parameter is ignored.
	 * 
	 * @param query Full text {@link String} search parameter. Should return any feeds matching this string
	 * @param content parameter of type {@link Content} describing the type of results
	 * @param tag Returns feeds containing datastreams tagged with the search query
	 * @param user Returns feeds created by the user specified.
	 * @param units Returns feeds containing datastreams with units specified by the search query.
	 * @param status Parameter of type {@link Status}
	 * @param order Parameter of type {@link Order}. Used for ordering the results.
	 * @param show_user Include user login and user level for each feed. {@link Boolean} with possible values: true, false (default)
	 * @param lat Used to find feeds located around this latitude. Used if ids/_datastreams_ are not specified.
	 * @param lon Used to find feeds located around this longitude. Used if ids/_datastreams_ are not specified.
	 * @param distance search radius
	 * @param distance_units miles or kms
	 * @return Array of {@link Feed} objects
	 * @throws CosmException
	 */
	public Feed[] getFeeds(String query,Content content,String tag,String user,String units,Status status,Order order,Boolean show_user,Double lat,Double lon,Double distance,DistanceUnit distance_units) throws CosmException {
		String q = "";
		boolean bAdd = false;

		if ( query != null ) {		
			if ( bAdd ) q += '&';
			q += "q=" + query;
			bAdd = true;
		}
		if ( content != null ) {
			if ( bAdd ) q += '&';
			q += "content=" + content.toString();
			bAdd = true;
		}		
		if ( tag != null ) {
			if ( bAdd ) q += '&';
			q += "tag=" + tag;
			bAdd = true;			
		}
		if ( user != null ) {
			if ( bAdd ) q += '&';
			q += "user=" + user;
			bAdd = true;
		}
		if ( units != null ) {
			if ( bAdd ) q += '&';
			q += "units=" + units;
			bAdd = true;
		}
		if ( status != null ) {
			if ( bAdd ) q += '&';
			q += "status=" + status.toString();
			bAdd = true;
		}		
		if ( order != null ) {
			if ( bAdd ) q += '&';
			q += "order=" + order.toString();
			bAdd = true;
		}		
		if ( show_user != null ) {
			if ( bAdd ) q += '&';
			q += "show_user=" + show_user.toString();
			bAdd = true;			
		}
		if ( lat != null ) {
			if ( bAdd ) q += '&';
			q += "lat=" + lat;
			bAdd = true;						
		}
		if ( lon != null ) {
			if ( bAdd ) q += '&';
			q += "lon=" + lon;
			bAdd = true;						
		}
		if ( distance != null ) {
			if ( bAdd ) q += '&';
			q += "distance=" + distance;
			bAdd = true;						
		}
		if ( distance_units != null ) {
			if ( bAdd ) q += '&';
			q += "distance_units=" + distance_units.toString();
			bAdd = true;									
		}

		try {
			URI uri = new URI(API_SCHEME,API_HOST, API_VERSION + API_RESOURCE_FEEDS + JSON_FILE_EXTENSION,q,null);
			HttpGet hr = new HttpGet(uri);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toFeeds(body);				
			}
            throw new CosmException(statusLine,body);			
		} catch ( IOException e) {		
			throw new CosmException(IO_EXCEPTION_MESSAGE);
		} catch ( URISyntaxException e ) {
			throw new CosmException(URL_SYNTAX_EXCEPTION_MESSAGE);
		}
	}

	// get feeds
	public Feed[] getFeeds() throws CosmException {
		return getFeeds(null,null,null,null,null,null,null,null);
	}

	// delete feed
	public void deleteFeed(Integer feedid) throws CosmException {
		try {
			HttpDelete hr = new HttpDelete(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/" + feedid);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200) {
				throw new CosmException(response.getStatusLine().toString());				
			}			
		} catch ( Exception e ) {		
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}		
	}

	// create feed
	public Feed createFeed(Feed feed) throws CosmException {
		try {
			HttpPost hr = new HttpPost(API_BASE_URL_V2 + API_RESOURCE_FEEDS + JSON_FILE_EXTENSION);
			hr.setEntity(new StringEntity(feed.toJSONObject().toString()));
			HttpResponse response = this.client.execute(hr);			
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 201 ) {
				String a[] = response.getHeaders(HEADER_PARAM_LOCATION)[0].getValue().split("/");
				Integer feedid = Integer.valueOf(a[a.length -1]);
				this.client.getBody(response);
				return this.getFeed(feedid.intValue());
			}
			
            throw new CosmException(response.getStatusLine().toString());						
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in create Feed" + e.getMessage());
		}
	}

	// update feed
	public void updateFeed(Feed feed) throws CosmException {
		try {
			HttpPut hr = new HttpPut(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/" + feed.getId() + JSON_FILE_EXTENSION);
			hr.setEntity(new StringEntity(feed.toJSONObject().toString()));
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() != 200 ) {
				throw new CosmException(response.getStatusLine().toString());												
			}
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in create Feed");
		}
	}

	// create group
	public Group createGroup(Group group) throws CosmException {
		try {
			HttpPost hr = new HttpPost(API_BASE_URL_V2 + "groups" + JSON_FILE_EXTENSION);
			hr.setEntity(new StringEntity(group.toJSONObject().toString()));
			HttpResponse response = this.client.execute(hr);			
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 201 ) {
				return CosmFactory.toGroup(this.client.getBody(response));
			}
			
            throw new CosmException(response.getStatusLine().toString());						
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in create Group" + e.getMessage());
		}

	}

	// get group
	public Group getGroup(String groupid) throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "groups/"+groupid+JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200 ) {
				return CosmFactory.toGroup(this.client.getBody(response));
			}
			
            throw new CosmException(response.getStatusLine().toString());
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in getGroup" + e.getMessage());
		}
	}

	// get groups
	public Group[] getGroups() throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "groups" + JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200 ) {
				return CosmFactory.toGroups(this.client.getBody(response));
			}
			
            throw new CosmException(response.getStatusLine().toString());
		} catch ( Exception e ) {
			throw new CosmException("Caught exception in getGroups");
		}
	}

	// update group
	public void updateGroup(Group group) throws CosmException {
		try {
			HttpPut hr = new HttpPut(API_BASE_URL_V2 + "groups/" + group.getGroupid() + JSON_FILE_EXTENSION);
			String requestBody = group.toJSONObject().toString();
			hr.setEntity(new StringEntity(requestBody));
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if (( statusLine.getStatusCode() != 200 )&&(  statusLine.getStatusCode() != 201)) {
				throw new CosmException(response.getStatusLine().toString());																
			}
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in update group");
		}
	}

	// delete group
	public void deleteGroup(String groupid) throws CosmException {
		try {
			HttpDelete hr = new HttpDelete(API_BASE_URL_V2 + "groups/"+groupid);			
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() != 200 ) {
				throw new CosmException(response.getStatusLine().toString());																				
			}
		} catch ( Exception e) {		
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}		
	}

	// get datastream
	public Datastream getDatastream(Integer feedid, String datastreamid) throws CosmException {
		try {
			HttpGet request = new HttpGet(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/" + feedid + "/datastreams/"+datastreamid+JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200 ) {
				return CosmFactory.toDatastream(this.client.getBody(response));
			}
            throw new HttpException(statusLine.toString());
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}
	}

	// get datastreams
	public Datastream[] getDatastreams(Integer feedid) throws CosmException {
		try {
			Feed feed = getFeed(feedid.intValue());
			return feed.getDatastreams();
		} catch ( Exception e ) {
			throw new CosmException(e.getMessage());
		}

	}

	// create datastream
	public Datastream createDatastream(Integer feedid, Datastream datastream) throws CosmException {
		try {
			HttpPost request = new HttpPost(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams" + JSON_FILE_EXTENSION);
			JSONObject jo = new JSONObject();
			jo.put("version", Cosm.VERSION);

			JSONArray ja = new JSONArray();
			ja.put(datastream.toJSONObject());
			jo.put("datastreams",ja);


			request.setEntity(new StringEntity(jo.toString()));
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 201 ) {
				String a[] = response.getHeaders(HEADER_PARAM_LOCATION)[0].getValue().split("/");
				String datastreamid = a[a.length -1];
				this.client.getBody(response);
				return this.getDatastream(feedid,datastreamid);
			}
			
            throw new HttpException(response.getStatusLine().toString());
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in create Datastream" + e.getMessage());
		}
	}

	// update datastream
	public void updateDatastream(Integer feedid,String datastreamid, Datastream datastream) throws CosmException {
		try {
			HttpPut request = new HttpPut(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+datastreamid+JSON_FILE_EXTENSION);
			request.setEntity(new StringEntity(datastream.toJSONObject().toString()));
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200 ) {
				this.client.getBody(response);
				return;
			}
			
            throw new HttpException(statusLine.toString());
		} catch ( Exception e ) {
			throw new CosmException(e.getMessage());
		}
	}

	// delete datastream
	public void deleteDatastream(Integer feedid,String datastreamid) throws CosmException {
		try {
			HttpDelete hr = new HttpDelete(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+ datastreamid);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() != 200 ) {
				throw new CosmException(response.getStatusLine().toString());																				
			}
		} catch ( Exception e) {		
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}		
	}


	// create datapoint
	public void createDatapoint(Integer feedid,String datastreamid,Datapoint datapoint) throws CosmException {
		try {
			HttpPost request = new HttpPost(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+datastreamid+"/datapoints" + JSON_FILE_EXTENSION);
			JSONObject jo = new JSONObject();
			JSONArray ja = new JSONArray();
			ja.put(datapoint.toJSONObject());
			jo.put("datapoints", ja);
			request.setEntity(new StringEntity(jo.toString()));
			HttpResponse response = this.client.execute(request);			
			StatusLine statusLine = response.getStatusLine();
			this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				throw new CosmException(response.getStatusLine().toString());								
			}						
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in create datapoint" + e.getMessage());
		}

	}

	// create datapoints
	public void createDatapoints(Integer feedid,String datastreamid,Datapoint[] datapoints) throws CosmException {
		try {
			HttpPost request = new HttpPost(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+datastreamid+"/datapoints" + JSON_FILE_EXTENSION);
			JSONObject jo = new JSONObject();

			JSONArray ja = new JSONArray();
			for(int i=0;(i<datapoints.length);i++) {			
				ja.put(datapoints[i].toJSONObject());				
			}
			jo.put("datapoints", ja);
			request.setEntity(new StringEntity(jo.toString()));
			HttpResponse response = this.client.execute(request);			
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				JSONObject ej = new JSONObject(body);
				throw new CosmException(ej.getString("errors"));
			}						
		} catch ( Exception e) {
			throw new CosmException("Caught exception in create datapoint" + e.getMessage());
		}

	}

	// update datapoint
	// Cosm documentation says, it's a post. It is in fact a PUT
	public void updateDatapoint(Integer feedid,String datastreamid,Datapoint datapoint) throws CosmException {
		try {
			HttpPut request = new HttpPut(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/" + feedid + "/datastreams/"+datastreamid+"/datapoints/"+datapoint.getAt() + JSON_FILE_EXTENSION);
			JSONObject jo = new JSONObject();
			jo.put("value",datapoint.getValue());
			request.setEntity(new StringEntity(jo.toString()));
			HttpResponse response = this.client.execute(request);			
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				if ( body.length() > 0 ) {
					JSONObject ej = new JSONObject(body);
					throw new CosmException(ej.getString("errors"));				
				}
				
                throw new CosmException(statusLine.toString());
			}
		} catch ( Exception e) {
			throw new CosmException("Caught exception in update datapoint: " + e.getMessage());
		}
	}

	// get a datapoint
	public Datapoint getDatapoint(Integer feedid, String datastreamid,String at) throws CosmException {
		try {
			HttpGet request = new HttpGet(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+datastreamid+"/datapoints/"+ at + JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200 ) {
				return CosmFactory.toDatapoint(this.client.getBody(response));
			}
			
            throw new HttpException(statusLine.toString());
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}	
	}

	// deleting a datapoint
	public void deleteDatapoint(Integer feedid, String datastreamid,String at) throws CosmException {
		try {
			HttpDelete request = new HttpDelete(API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+datastreamid+"/datapoints/"+ at);			
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				throw new HttpException(statusLine.toString());
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}	
	}

	// deleting multiple datapoints
	public void deleteDatapoints(Integer feedid, String datastreamid,String start, String end, String duration) throws CosmException {
		try {
			String url = API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+datastreamid+"/datapoints?";
			boolean bAdd = false;
			if ( start != null ) {
				if ( bAdd ) url += '&';
				url += "start=" + start;
				bAdd = true;
			}
			if ( end != null ) {
				if ( bAdd ) url += '&';
				url += "end=" + end;
				bAdd = true;
			}
			if ( duration != null ) {
				if ( bAdd ) url += '&';
				url += "duration=" + duration;
				bAdd = true;
			}
			HttpDelete request = new HttpDelete(url);
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() != 200 ) {
				throw new HttpException(statusLine.toString());
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}	
	}


	public Waypoint[] getWaypoints(Integer feedid, String start, String end, String duration,Integer interval, Boolean find_previous, Interval_type interval_type,String timezone) throws CosmException {
		try {
			String url = API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+JSON_FILE_EXTENSION+"?";

			boolean bAdd = false;
			if ( start != null ) {
				if ( bAdd ) url += '&';
				url += "start=" + start;
				bAdd = true;
			}
			if ( end != null ) {
				if ( bAdd ) url += '&';
				url += "end=" + end;
				bAdd = true;
			}
			if ( duration != null ) {
				if ( bAdd ) url += '&';
				url += "duration=" + duration;
				bAdd = true;
			}
			if ( interval != null ) {
				if ( bAdd ) url += '&';
				url += "interval=" + interval;
				bAdd = true;
			}
			if ( find_previous != null ) {
				if ( bAdd ) url += '&';
				url += "find_previous=" + find_previous.toString();
				bAdd = true;
			}
			if ( interval_type != null ) {
				if ( bAdd ) url += '&';
				url += "interval_type=" + interval_type.toString();
				bAdd = true;
			}

			if ( timezone != null ) {
				if ( bAdd ) url += '&';			
				url += "timezone=" + timezone;				
			}

			HttpGet request = new HttpGet(url);				 
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() == 200 ) {
				Feed feed = CosmFactory.toFeed(body);
				Location location = feed.getLocation();
				Waypoint[] waypoints = location.getWaypoints();
				return waypoints;
			}
			
            throw new CosmException(statusLine,body);

		} catch ( CosmException e ) {
			throw e;
		} catch ( NullPointerException e ) {
			throw new CosmException(NULL_POINTER_EXCEPTION_MESSAGE);
		} catch ( IOException e ) {
			throw new CosmException(IO_EXCEPTION_MESSAGE);
		}
	}



	// listing all datapoints, historical queries
	public Datapoint[] getDatapoints(Integer feedid, String datastreamid, String start, String end, String duration,Integer interval, Boolean find_previous, Interval_type interval_type, int per_page,String timezone) throws CosmException {
		try {
			String url = API_BASE_URL_V2 + API_RESOURCE_FEEDS + "/"+feedid+"/datastreams/"+datastreamid + JSON_FILE_EXTENSION + "?";

			boolean bAdd = false;
			if ( start != null ) {
				if ( bAdd ) url += '&';
				url += "start=" + start;
				bAdd = true;
			}
			if ( end != null ) {
				if ( bAdd ) url += '&';
				url += "end=" + end;
				bAdd = true;
			}
			if ( duration != null ) {
				if ( bAdd ) url += '&';
				url += "duration=" + duration;
				bAdd = true;
			}
			if ( interval != null ) {
				if ( bAdd ) url += '&';
				url += "interval=" + interval;
				bAdd = true;
			}
			if ( find_previous != null ) {
				if ( bAdd ) url += '&';
				url += "find_previous=" + find_previous.toString();
				bAdd = true;
			}
			if ( interval_type != null ) {
				if ( bAdd ) url += '&';
				url += "interval_type=" + interval_type.toString();
				bAdd = true;
			}

			if ( timezone != null ) {
				if ( bAdd ) url += '&';			
				url += "timezone=" + timezone;				
			}

			if ( bAdd ) url += '&';			
			url += "per_page=" + per_page;



			ArrayList<Datapoint> datapoints = new ArrayList<Datapoint>();

			boolean bContinue = true;
			int page = 1;
			while ( bContinue ) {


				HttpGet request = new HttpGet(url + "&page=" + page);				 
				HttpResponse response = this.client.execute(request);
				StatusLine statusLine = response.getStatusLine();
				String body = this.client.getBody(response);
				if ( statusLine.getStatusCode() == 200 ) {
					Datapoint[] newDatapoints = CosmFactory.toDatapoints(body);
					if ( newDatapoints.length < per_page ) {
						bContinue = false;
					} else {
						page++;
					}

					for ( Datapoint datapoint : newDatapoints ) {
						datapoints.add(datapoint);
					}
				} else {
					throw new CosmException(statusLine,body);
				}
			}

			return datapoints.toArray(new Datapoint[datapoints.size()]);

		} catch ( CosmException e ) {
			throw e;
		} catch ( IOException e ) {
			throw new CosmException("IO Exception when communicating with Cosm");
		}
	}

	// create apikey
	public Apikey createApikey(Apikey apikey) throws CosmException {
		try {
			HttpPost request = new HttpPost(API_BASE_URL_V2 + "keys" + JSON_FILE_EXTENSION);
			request.setEntity(new StringEntity(apikey.toJSONObject().toString()));
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);			
			if ( statusLine.getStatusCode() == 201 ) {
				String a[] = response.getHeaders(HEADER_PARAM_LOCATION)[0].getValue().split("/");
				String key = a[a.length -1];				
				return this.getApikey(key);
			}
            throw new CosmException(statusLine,body);
		} catch ( CosmException e ) {
			throw e;
		} catch ( IOException e ) {
			throw new CosmException("IO Exception when communicating with Cosm");
		} catch ( JSONException e ) {
			throw new CosmException("Problem during JSON parsing");
		}
	}

	// get an apikey
	public Apikey getApikey(String apikey) throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "keys/"+apikey+JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toApikey(this.client.getBody(response));				
			}
            throw new CosmException(response.getStatusLine().toString());						
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException("error in getApikey");
		}
	}

	// get a list of apikeys 
	public Apikey[] getApikeys() throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "keys" + JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toApikeys(this.client.getBody(response));				
			}
            throw new CosmException(response.getStatusLine().toString());						
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException("error in getApikey");
		}
	}

	// deleting an apikey
	public void deleteApikey(String apikey) throws CosmException {
		try {
			HttpDelete request = new HttpDelete(API_BASE_URL_V2 + "keys/"+ apikey);			
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				throw new HttpException(statusLine.toString());
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}	

	}

	// get trigger
	public Trigger getTrigger(Integer id) throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "triggers/"+id+JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toTrigger(this.client.getBody(response));				
			}
            throw new CosmException(response.getStatusLine().toString());									
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException("error in getTrigger");
		}
	}

	// get triggers
	public Trigger[] getTriggers() throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "triggers" + JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toTriggers(this.client.getBody(response));				
			}
            throw new CosmException(response.getStatusLine().toString());									
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException("error in getTrigger");
		}
	}

	// delete trigger
	public void deleteTrigger(String id) throws CosmException {
		try {
			HttpDelete request = new HttpDelete(API_BASE_URL_V2 + "triggers/"+ id);			
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				throw new CosmException(statusLine,body);
			}
		} catch ( Exception e ) {
			throw new CosmException(e);
		}	
	}

	// create trigger
	public Trigger createTrigger(Trigger trigger) throws CosmException {
		try {
			HttpPost request = new HttpPost(API_BASE_URL_V2 + "triggers" + JSON_FILE_EXTENSION);
			request.setEntity(new StringEntity(trigger.toJSONObject().toString()));
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);			
			if ( statusLine.getStatusCode() == 201 ) {
				String a[] = response.getHeaders(HEADER_PARAM_LOCATION)[0].getValue().split("/");
				Integer id = Integer.valueOf(a[a.length -1]);				
				return this.getTrigger(id);
			}
            throw new Exception(body);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException("error while creating new apikey");
		}		
	}

	// update trigger
	public void updateTrigger(Trigger trigger) throws CosmException {
		try {
			HttpPut request = new HttpPut(API_BASE_URL_V2 + "triggers/"+ trigger.getId() + JSON_FILE_EXTENSION);
			request.setEntity(new StringEntity(trigger.toJSONObject().toString()));
			HttpResponse response = this.client.execute(request);			
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				if ( body.length() > 0 ) {
					JSONObject ej = new JSONObject(body);
					throw new CosmException(ej.getString("errors"));				
				}
                throw new CosmException(statusLine.toString());
			}
		} catch ( Exception e) {
			throw new CosmException("Caught exception in update trigger: " + e.getMessage());
		}
	}

	// get user
	public User getUser(String login) throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "users/"+login+JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toUser(this.client.getBody(response));				
			}
            throw new CosmException(response.getStatusLine().toString());									
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException("error in getUser");
		}
	}

	// get users
	public User[] getUsers() throws CosmException {
		try {
			HttpGet hr = new HttpGet(API_BASE_URL_V2 + "users" + JSON_FILE_EXTENSION);
			HttpResponse response = this.client.execute(hr);
			StatusLine statusLine = response.getStatusLine();
			if ( statusLine.getStatusCode() == 200) {
				return CosmFactory.toUsers(this.client.getBody(response));				
			}
            throw new CosmException(response.getStatusLine().toString());									
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException("error in getUsers");
		}
	}

	// delete user
	public void deleteUser(String login) throws CosmException {
		try {
			HttpDelete request = new HttpDelete(API_BASE_URL_V2 + "users/"+ login + JSON_FILE_EXTENSION);			
			HttpResponse response = this.client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				throw new HttpException(statusLine.toString());
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new CosmException(e.getMessage());
		}			
	}

	// update user
	public void updateUser(String login,User user) throws CosmException {
		try {
			HttpPut request = new HttpPut(API_BASE_URL_V2 + "users/"+ login + JSON_FILE_EXTENSION);
			request.setEntity(new StringEntity(user.toJSONObject().toString()));
			HttpResponse response = this.client.execute(request);			
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() != 200 ) {
				if ( body.length() > 0 ) {
					JSONObject ej = new JSONObject(body);
					throw new CosmException(ej.getString("errors"));				
				}
                throw new CosmException(statusLine.toString());
			}
		} catch ( Exception e) {
			throw new CosmException("Caught exception in update trigger: " + e.getMessage());
		}
	}

	// create user
	public void createUser(User user) throws CosmException {
		try {
			HttpPost request = new HttpPost(API_BASE_URL_V2 + "users" + JSON_FILE_EXTENSION);
			request.setEntity(new StringEntity(user.toJSONObject().toString()));
			HttpResponse response = this.client.execute(request);			
			StatusLine statusLine = response.getStatusLine();
			String body = this.client.getBody(response);
			if ( statusLine.getStatusCode() == 201 ) {
				return;
			}
            if ((body!=null)&&(body.length() > 0 )) {					
            	JSONObject ej = new JSONObject(body);
            	throw new CosmException(ej.getString("errors"));				
            }
            throw new CosmException(statusLine.toString());
		} catch ( Exception e) {
			e.printStackTrace();
			throw new CosmException("Caught exception in update createUser: " + e.getMessage());
		}
	}

	//TODO: get permissions	
	//TODO: show permissions

}
