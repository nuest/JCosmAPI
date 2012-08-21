package Cosm;

public class Color {
	private int red;
	private int blue;
	private int green;
	public int getRed() {
		return this.red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getBlue() {
		return this.blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	public int getGreen() {
		return this.green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Color [red=");
        builder.append(this.red);
        builder.append(", blue=");
        builder.append(this.blue);
        builder.append(", green=");
        builder.append(this.green);
        builder.append("]");
        return builder.toString();
    }
	
}
