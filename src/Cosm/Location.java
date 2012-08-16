package Cosm;

import org.json.JSONException;
import org.json.JSONObject;


public class Location {
	
	private String name;
	
	private Double lat;
	
	private Double lon;
	
	private Double elevation;
	
	private Exposure exposure;
	
	private Domain domain;
	
	private Disposition disposition;
	
	private Waypoint[] waypoints;
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();		
		
		jo.putOpt("name", this.name);
		jo.putOpt("lat",this.lat);
		jo.putOpt("lon", this.lon);
		jo.putOpt("elevation", this.elevation);
		jo.putOpt("exposure", this.exposure != null ? this.exposure.toString() : null);
		jo.putOpt("domain", this.domain  != null ? this.domain.toString() : null);
		jo.putOpt("disposition", this.disposition  != null ? this.disposition.toString() : null);
		
		return jo;
	}
	
	
	
	
	public Waypoint[] getWaypoints() {
		return this.waypoints;
	}




	public void setWaypoints(Waypoint[] waypoints) {
		this.waypoints = waypoints;
	}




	public void setLat(String lat){
		try{
			this.lat = Double.valueOf(lat);
		}catch  (Exception c){
			this.lat = Double.valueOf(0.0d);
		}
	}

	
	public void setLon(String lon){
		try{
			this.lon= Double.valueOf(lon);
		}catch  (Exception c){
			this.lon = Double.valueOf(0.0d);
		}
	}

	
	public void setElevation(String elevation) {
		try{
			this.elevation = Double.valueOf(elevation);
		}catch  (Exception c){
			this.elevation = Double.valueOf(0.0d);
		}
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lat
	 */
	public Double getLat() {
		return this.lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public Double getLon() {
		return this.lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}

	/**
	 * @return the elevation
	 */
	public Double getElevation() {
		return this.elevation;
	}

	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	/**
	 * @return the exposure
	 */
	public Exposure getExposure() {
		return this.exposure;
	}

	/**
	 * @param exposure the exposure to set
	 */
	public void setExposure(Exposure exposure) {
		this.exposure = exposure;
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return this.domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * @return the disposition
	 */
	public Disposition getDisposition() {
		return this.disposition;
	}

	/**
	 * @param disposition the disposition to set
	 */
	public void setDisposition(Disposition disposition) {
		this.disposition = disposition;
	}

}