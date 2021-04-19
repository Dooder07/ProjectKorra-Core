package com.projectkorra.core.util.color;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;

public class Gradient {

	private TextColor[] endpoints;
	private int[][] deltas;
	
	private Gradient(TextColor...colors) {
		endpoints = colors;
		deltas = new int[colors.length - 1][3];
		for (int i = 0; i < colors.length - 1; ++i) {
			deltas[i][0] = colors[i + 1].getRed() - colors[i].getRed();
			deltas[i][1] = colors[i + 1].getGreen() - colors[i].getGreen();
			deltas[i][2] = colors[i + 1].getBlue() - colors[i].getBlue();
		}
	}
	
	public String apply(String text) {
		Validate.isTrue(text.length() >= endpoints.length, "Text length must be at least the amount of colors!");
		String[] parts = divide(text, endpoints.length - 1);
		TextColor gradient = endpoints[0].clone();
		int[] shifting;
		
		StringBuilder result = new StringBuilder().append(gradient.toChatColor()).append(parts[0].charAt(0));
		parts[0] = parts[0].substring(1, parts[0].length());
		
		for (int i = 0; i < endpoints.length - 1; ++i) {
			System.out.println(parts[i].length());
			shifting = realDeltas(i, parts[i].length());
			
			for (char c : parts[i].toCharArray()) {
				result.append(gradient.shift(shifting[0], shifting[1], shifting[2]).toChatColor()).append(c);
			}
		}
		
		return result.toString();
	}
	
	private String[] divide(String text, int parts) {
		String[] result = new String[parts];
		float n = text.length() / (float) parts;
		for (int i = 0; i < parts; i++) {
			result[i] = text.substring(Math.round(i * n), Math.round((i + 1) * n));
		}
		return result;
	}
	
	private int[] realDeltas(int endpoint, int chars) {
		int[] deltas = new int[3];
		chars = (chars - 1 > 0) ? chars - 1 : 1;
		deltas[0] = this.deltas[endpoint][0] / chars;
		deltas[1] = this.deltas[endpoint][1] / chars;
		deltas[2] = this.deltas[endpoint][2] / chars;
		return deltas;
	}
	
	public static Gradient of(TextColor...colors) {
		Validate.isTrue(colors.length > 1, "A gradient requires at least 2 colors!");
		return new Gradient(colors);
	}
	
	public static Gradient of(Color...colors) {
		Validate.isTrue(colors.length > 1, "A gradient requires at least 2 colors!");
		TextColor[] textcolors = new TextColor[colors.length];
		for (int i = 0; i < colors.length; ++i) {
			textcolors[i] = TextColor.of(colors[i]);
		}
		return new Gradient(textcolors);
	}
}
