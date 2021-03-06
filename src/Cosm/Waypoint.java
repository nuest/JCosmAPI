package Cosm;

public class Waypoint {
	private String at;
	private Double lat;
	private Double lon;
	public String getAt() {
		return this.at;
	}
	public void setAt(String at) {
		this.at = at;
	}
	public Double getLat() {
		return this.lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return this.lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Waypoint [at=");
        builder.append(at);
        builder.append(", lat=");
        builder.append(lat);
        builder.append(", lon=");
        builder.append(lon);
        builder.append("]");
        return builder.toString();
    }

}
