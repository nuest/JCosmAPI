package Cosm;

import org.apache.http.StatusLine;
import org.json.JSONException;
import org.json.JSONObject;

public class CosmException extends Exception {
	/**
     * 
     */
    private static final long serialVersionUID = -3461882529148695038L;
    private StatusLine statusLine;
	private String title;
	private String errorMessage;
	private Exception exception;
	
	public CosmException(Exception e) {
		this.statusLine = null;
		this.errorMessage = "";
		this.title = "JCosmAPI Caught an exception";
		this.exception = e;
	}
	
	public CosmException(StatusLine statusLine,String body) {
		this.statusLine = statusLine;
		try {
			JSONObject jo = new JSONObject(body);
			this.title = jo.getString("title");
			this.errorMessage = jo.getString("errors");
		} catch ( JSONException e ) {
			this.title = "Could not parse response body";
			this.errorMessage = body;
		}		
	}
	
	
	
	public CosmException(String errorMessage) {		
		super(errorMessage);
	}
	
	@Override
	public String getMessage() {
		if (( this.title != null )&&(this.errorMessage!=null)) {
			return this.title + ". " + this.errorMessage;
		}
		if ( this.errorMessage != null ) {
			return this.errorMessage;
		}
		if ( this.statusLine != null ) {
			return this.statusLine.toString();
		}
		return super.getMessage();		
	}

    public Exception getException() {
        return this.exception;
    }

}