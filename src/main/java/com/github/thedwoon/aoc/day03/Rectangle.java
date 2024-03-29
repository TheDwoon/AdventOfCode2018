package com.github.thedwoon.aoc.day03;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rectangle {
	private static final Pattern LINE_PATTERN = Pattern.compile("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
	/**
	 * 
	 */
	private final int id;
	private final int x;
	private final int y;
	private final int height;
	private final int width;
	
	public Rectangle(String line) {
		Matcher m = LINE_PATTERN.matcher(line);		
		
		if (m.find() && m.groupCount() == 5) {
			this.id = Integer.parseInt(m.group(1));
			this.x = Integer.parseInt(m.group(2));
			this.y = Integer.parseInt(m.group(3));
			this.width = Integer.parseInt(m.group(4));
			this.height = Integer.parseInt(m.group(5));
		} else {
			throw new IllegalArgumentException("line did not match pattern: " + line);
		}
	}
	
	public boolean overlap(Rectangle other) {
		return x + width > other.x && x < other.x + other.width 
				&& y + height > other.y && y < other.y + other.height; 
	}
	
	public boolean overlap(List<Rectangle> rectangles) {
		return rectangles.stream().filter(r -> r != this).map(this::overlap).filter(Boolean.TRUE::equals).count() > 0;
	}
	
	public int getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}