package com.github.thedwoon.aoc.day06;

import com.github.thedwoon.aoc.Day06;

public class Point {
	public final int pointId = Day06.pointId++;
	public final int x;
	public final int y;
	
	public int coveredArea = 0;
	
	public Point(int x , int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(String[] args) {
		this.x = Integer.parseInt(args[0]);
		this.y = Integer.parseInt(args[1]);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int manhattenDistance(int tx, int ty) {
		return Math.abs(x - tx) + Math.abs(y - ty);
	}
	
	@Override
	public String toString() {
		return "Point " + pointId + " (" + x + ", " + y + "): " + coveredArea;			
	}		
}