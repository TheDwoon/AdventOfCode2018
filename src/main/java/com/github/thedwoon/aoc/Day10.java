package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.thedwoon.aoc.day10.Star;

public final class Day10 extends AbstractDay<List<Star>> {
	private static final Pattern PATTERN = Pattern.compile("position=<([ +-]*\\d+), ([ +-]*\\d+)> velocity=<([ +-]*\\d+), ([ +-]*\\d+)>");

	public Day10() {
		super();
	}
	
	public static void main(String[] args) {
		new Day10().run();
	}

	@Override
	protected List<Star> getInput() {
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
	protected String runPart1(List<Star> input) {
		List<Star> stars = getInput();
		long smallestCoveredArea = coveredArea(stars);
		
		long currentCoveredArea;
		do {
			stars.forEach(Star::advance);
			currentCoveredArea = coveredArea(stars);
			if (currentCoveredArea < smallestCoveredArea)
				smallestCoveredArea = currentCoveredArea;
									
		} while (currentCoveredArea <= smallestCoveredArea);
		
		stars.forEach(Star::rewind);
		StringBuilder sb = new StringBuilder();
		sb.append('\n');
		printStars(stars, sb);
		return sb.toString();
	}
	
	@Override
	protected String runPart2(List<Star> stars) {
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
		return Integer.toString(currentSecond);
	}
	
	private long coveredArea(List<Star> stars) {
		final long minX = stars.stream().mapToInt(Star::getX).min().orElse(0);
		final long maxX = stars.stream().mapToInt(Star::getX).max().orElse(0);
		final long minY = stars.stream().mapToInt(Star::getY).min().orElse(0);
		final long maxY = stars.stream().mapToInt(Star::getY).max().orElse(0);
		
		return (maxX - minX) * (maxY - minY);
	}
	
	private void printStars(List<Star> stars, StringBuilder sb) {
		Collections.sort(stars);
		final int minX = stars.stream().mapToInt(Star::getX).min().orElse(0);
		final int maxX = stars.stream().mapToInt(Star::getX).max().orElse(0);
		final int minY = stars.stream().mapToInt(Star::getY).min().orElse(0);
		final int maxY = stars.stream().mapToInt(Star::getY).max().orElse(0);
		
		Iterator<Star> it = stars.iterator();
		Star s = it.hasNext() ? it.next() : null; 				
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				if (s != null && s.getX() == x && s.getY() == y) {
					while (s != null && s.getX() == x && s.getY() == y) {
						s = it.hasNext() ? it.next() : null;
					}
						
					sb.append(" # ");
				} else {
					sb.append(" . ");
				}				
			}
			
			sb.append('\n');
		}
	}
}
