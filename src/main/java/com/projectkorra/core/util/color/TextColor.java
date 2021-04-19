package com.projectkorra.core.util.color;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;

import net.md_5.bungee.api.ChatColor;
 
public class TextColor {
 
    private int[] rgb = new int[3];
    
    private TextColor(int r, int g, int b) {
        this.rgb[0] = r;
        this.rgb[1] = g;
        this.rgb[2] = b;
    }
    
    public int getRed() {
        return rgb[0];
    }
    
    /**
     * Set the red value for this color, automatically bounded
     * between 0 and 255 (inclusive)
     * @param blue The amount to set the red
     * @return the same {@link TextColor} object
     */
    public TextColor setRed(int red) {
    	rgb[0] = Math.min(Math.max(red, 0), 255);
    	return this;
    }
    
    public int getGreen() {
        return rgb[1];
    }
    
    /**
     * Set the green value for this color, automatically bounded
     * between 0 and 255 (inclusive)
     * @param blue The amount to set the green
     * @return the same {@link TextColor} object
     */
    public TextColor setGreen(int green) {
    	rgb[1] = Math.min(Math.max(green, 0), 255);
    	return this;
    }
    
    public int getBlue() {
        return rgb[2];
    }
    
    /**
     * Set the blue value for this color, automatically bounded
     * between 0 and 255 (inclusive)
     * @param blue The amount to set the blue
     * @return the same {@link TextColor} object
     */
    public TextColor setBlue(int blue) {
    	rgb[2] = Math.min(Math.max(blue, 0), 255);
    	return this;
    }
    
    /**
     * Shift this color by the given amounts for each r, g, b
     * @param deltaR The amount to shift the red value
     * @param deltaG The amount to shift the green value
     * @param deltaB The amount to shift the blue value
     * @return the same {@link TextColor} object
     */
    public TextColor shift(int deltaR, int deltaG, int deltaB) {
    	return setRed(rgb[0] + deltaR).setGreen(rgb[1] + deltaG).setBlue(rgb[2] + deltaB);
    }
    
    @Override
    public int hashCode() {
        return rgb.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof TextColor && rgb.equals(((TextColor) other).rgb);
    }
    
    @Override
    public String toString() {
        return "HexColor{" + rgb[0] + "," + rgb[1] + "," + rgb[2] + "}";
    }
    
    @Override
    public TextColor clone() {
    	return new TextColor(rgb[0], rgb[1], rgb[2]);
    }
    
    public TextColor copy(TextColor other) {
    	return setRed(other.getRed()).setGreen(other.getGreen()).setBlue(other.getBlue());
    }
    
    public TextColor copy(Color other) {
    	return setRed(other.getRed()).setGreen(other.getGreen()).setBlue(other.getBlue());
    }
    
    public String toHexadecimal() {
    	StringBuilder hex = new StringBuilder("#");
    	
    	for (int i = 0; i < 3; ++i) {
    		float quotient = rgb[i] / 16f;
    		
    		hex.append(Integer.toHexString((int) quotient));
    		hex.append(Integer.toHexString((int) ((quotient - (int) quotient) * 16)));
    	}
    	
    	return hex.toString();
    }
    
    public ChatColor toChatColor() {
        return ChatColor.of(toHexadecimal());
    }
    
    public Color toBukkit() {
        return Color.fromRGB(rgb[0], rgb[1], rgb[2]);
    }
    
    /**
     * Create a color from the given hexadecimal color code
     * @param hex The hexadecimal number for the color, between [000000, ffffff]
     * @return {@link TextColor} object of the given hexadecimal color code
     */
    public static TextColor of(String hex) {
        Validate.notNull(hex, "Hexadecimal cannot be null for a HexColor");
        hex = hex.replace("#", "");
        Validate.isTrue(hex.length() == 6, "Hexadecimal can only be 6 characters after the # for a HexColor");
        return new TextColor(Integer.parseInt(hex.substring(0, 2), 16), Integer.parseInt(hex.substring(2, 4), 16), Integer.parseInt(hex.substring(4, 6), 16));
    }
    
    /**
     * Create a color from the given Bukkit {@link org.bukkit.Color}
     * @param bukkit Bukkit defined {@link org.bukkit.Color}
     * @return {@link TextColor} object of the given bukkit {@link org.bukkit.Color}
     */
    public static TextColor of(Color bukkit) {
    	return new TextColor(bukkit.getRed(), bukkit.getGreen(), bukkit.getBlue());
    }
    
    /**
     * Create a color from the given r, g, b values, automatically
     * bounding the values between 0 and 255
     * @param red The red value of the color
     * @param green The green value of the color
     * @param blue The blue value of the color
     * @return {@link TextColor} object of the given rgb
     */
    public static TextColor of(int red, int green, int blue) {
        return new TextColor(Math.max(Math.min(255, red), 0), Math.max(Math.min(255, green), 0), Math.max(Math.min(255, blue), 0));
    }
    
    /**
     * Create a color from the given hue, saturation, and brightness values
     * @param hue Hue angle in degrees. 0 is red, 120 is green, and 240 is blue
     * @param sat Saturation value, must be in the range [0, 1]
     * @param val Brightness value, must be in the range [0, 1]
     * @return {@link TextColor} object of the given hsv
     */
    public static TextColor of(int hue, float sat, float val) {
    	Validate.isTrue(sat >= 0 && sat <= 1, "Saturation must be in the range [0, 1]");
    	Validate.isTrue(val >= 0 && val <= 1, "Brightness must be in the range [0, 1]");
    	hue = hue % 360;
    	return new TextColor(Math.round(hsv(0, hue, sat, val)), Math.round(hsv(8, hue, sat, val)), Math.round(hsv(4, hue, sat, val)));
    }
    
    private static float hsv(int n, int hue, float sat, float val) {
    	float k = (n + hue / 30f) % 12;
		return val - (sat * Math.min(val, 1 - val)) * Math.max(-1, Math.min(Math.min(k - 3, 9 - k), 1));
    }
}