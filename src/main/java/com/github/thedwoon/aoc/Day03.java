package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
	private static final Pattern LINE_PATTERN = Pattern.compile("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

	public static void main(String[] args) {
		Scanner scanner = new Scanner(Day03.class.getResourceAsStream("/DayThree.txt"));
		List<Rectangle> rects = new ArrayList<>();
		
		while (scanner.hasNextLine())
			rects.add(new Rectangle(scanner.nextLine()));
		
		scanner.close();
		
		stage1(rects);
		stage2(rects);
	}
	
	private static void stage1(List<Rectangle> input) {
		final int minX = input.stream().mapToInt(r -> r.getX()).min().getAsInt();
		final int maxX = input.stream().mapToInt(r -> r.getX() + r.getWidth()).max().getAsInt();
		final int minY = input.stream().mapToInt(r -> r.getY()).min().getAsInt();
		final int maxY = input.stream().mapToInt(r -> r.getY() + r.getHeight()).max().getAsInt();
		final int globalWidth = maxX - minX;
		final int globalHeight = maxY - minY;
		
		RectList[][] rects = new RectList[globalWidth][globalHeight];
		for (Rectangle rect : input) {
			for (int x = rect.getX(); x < rect.getX() + rect.getWidth(); x++) {
				for (int y = rect.getY(); y < rect.getY() + rect.getHeight(); y++) {
					if (rects[x - minX][y - minY] == null) {
						rects[x - minX][y - minY] = new RectList();
					} 
					
					rects[x - minX][y - minY].add(rect);
				}
			}
		}
		
		
		long count = Arrays.stream(rects).flatMap(Arrays::stream).filter(Objects::nonNull).filter(r -> r.size() > 1).count();
		System.out.println("Stage 1: " + count);
	}
	
	private static void stage2(final List<Rectangle> input) {
		int id = (int) input.stream().filter(r -> !r.overlap(input)).mapToInt(Rectangle::getId).findFirst().getAsInt();
		System.out.println("Stage 2: " + id);
	}
	
	private static class RectList extends ArrayList<Rectangle> {
		private static final long serialVersionUID = -2675597112374764695L;
	}
	
	private static class Rectangle {
		private final int id;
		private final int x;
		private final int y;
		private final int height;
		private final int width;
		
		private Rectangle(String line) {
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
		
		private Rectangle(int id, int x, int y, int width, int height) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
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
}
