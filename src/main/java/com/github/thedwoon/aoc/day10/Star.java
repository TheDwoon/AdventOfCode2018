package com.github.thedwoon.aoc.day10;

public class Star implements Comparable<Star> {
	private int x;
	private int y;
	private int vx;
	private int vy;
	
	public Star(int x, int y, int vx, int vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
	
	public void advance() {
		x += vx;
		y += vy;
	}
	
	public void rewind() {
		x -= vx;
		y -= vy;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public int compareTo(Star o) {
		int c = Integer.compare(y, o.y);
		if (c != 0)
			return c;
					
		return Integer.compare(x, o.x); 
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}