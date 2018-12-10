package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends AbstractDay {
	private static final Pattern PATTERN = Pattern.compile("position=<([ +-]*\\d+), ([ +-]*\\d+)> velocity=<([ +-]*\\d+), ([ +-]*\\d+)>");

	private List<Star> getInput() {
		List<Star> stars = new ArrayList<>();
		for (String line : getLines()) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find())
				throw new IllegalArgumentException(line);
			
			int x = Integer.parseInt(m.group(1).trim());
			int y = Integer.parseInt(m.group(2).trim());
			int vx = Integer.parseInt(m.group(3).trim());
			int vy = Integer.parseInt(m.group(4).trim());
			
			stars.add(new Star(x, y, vx, vy));
		}
		
		return stars;
	}
	
	@Override
	public void run() {
		List<Star> stars = getInput();
		
		int bestSecond = 0;
		long smallestCoveredArea = coveredArea(stars);
		
		int currentSecond = 0;
		long currentCoveredArea;
		do {
			currentSecond++;
			stars.forEach(Star::advance);
			currentCoveredArea = coveredArea(stars);
			if (currentCoveredArea < smallestCoveredArea)
				smallestCoveredArea = currentCoveredArea;
									
		} while (currentCoveredArea <= smallestCoveredArea);
		
		currentSecond--;
		stars.forEach(Star::rewind);
		printStars(stars);
		System.out.println("Stage 1: " + currentSecond);
	}

	private long coveredArea(List<Star> stars) {
		final long minX = stars.stream().mapToInt(Star::getX).min().orElse(0);
		final long maxX = stars.stream().mapToInt(Star::getX).max().orElse(0);
		final long minY = stars.stream().mapToInt(Star::getY).min().orElse(0);
		final long maxY = stars.stream().mapToInt(Star::getY).max().orElse(0);
		
		return (maxX - minX) * (maxY - minY);
	}
	
	private void printStars(List<Star> stars) {
		Collections.sort(stars);
		final int minX = stars.stream().mapToInt(Star::getX).min().orElse(0);
		final int maxX = stars.stream().mapToInt(Star::getX).max().orElse(0);
		final int minY = stars.stream().mapToInt(Star::getY).min().orElse(0);
		final int maxY = stars.stream().mapToInt(Star::getY).max().orElse(0);
		
		Iterator<Star> it = stars.iterator();
		Star s = it.hasNext() ? it.next() : null; 				
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				if (s != null && s.x == x && s.y == y) {
					while (s != null && s.x == x && s.y == y) {
						s = it.hasNext() ? it.next() : null;
					}
						
					System.out.print(" # ");
				} else {
					System.out.print(" . ");
				}				
			}
			
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		new Day10().run();
	}

	private class Star implements Comparable<Star> {
		private int x;
		private int y;
		private int vx;
		private int vy;
		
		private Star(int x, int y, int vx, int vy) {
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
}
