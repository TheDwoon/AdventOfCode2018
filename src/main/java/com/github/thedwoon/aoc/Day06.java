package com.github.thedwoon.aoc;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 extends AbstractDay {
	private static int pointId = 0;
	
	private List<Point> getInput() {
		return getLines().stream().map(line -> line.split(", ")).map(Point::new).collect(Collectors.toList());
	}
	
	@Override
	public void run() {
		List<Point> points = getInput();
		
		final int minX = points.stream().mapToInt(Point::getX).min().orElse(0);
		final int maxX = points.stream().mapToInt(Point::getX).max().orElse(0);
		final int minY = points.stream().mapToInt(Point::getY).min().orElse(0);
		final int maxY = points.stream().mapToInt(Point::getY).max().orElse(0);
		final int width = maxX + 1 - minX;
		final int height = maxY + 1 - minY;
	
		Point[][] board = new Point[width][height];
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				int bestDistance = Integer.MAX_VALUE;
				Point bestPoint = null;
				for (Point p : points) {
					int dist = p.manhattenDistance(x, y);
					if (dist < bestDistance) {
						bestDistance = dist;
						bestPoint = p;
					} else if (dist == bestDistance) {
						bestPoint = null;
					}
				}
				
				if (bestPoint != null) {
					bestPoint.coveredArea += 1;
					board[x - minX][y - minY] = bestPoint;
				}
			}
		}

		for (int i = 0; i < Math.max(width, height); i++) {
			if (i < width) {
				points.remove(board[i][0]);
				points.remove(board[i][height - 1]);
			}
			
			if (i < height) {
				points.remove(board[0][i]);
				points.remove(board[width - 1][i]);
			}
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Point p = board[i][j];				
				if (p != null)
					if (!points.contains(p)) 
						System.out.print(" - ");
					else if (p.x == i + minX && p.y == j + minY) 
						System.out.print(" X ");
					else
						System.out.printf("%2d ", p.pointId);
				else
					System.out.print(" . ");
			}
			
			System.out.println();
		}
		
		
		Collections.sort(points, (o1, o2) -> -Integer.compare(o1.coveredArea, o2.coveredArea));
		System.out.println("Stage 1: " + points.get(0));
		
		int totalCoveredArea = 0;
		points = getInput();
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				int summedDistance = 0;
				for (Point p : points) {
					summedDistance += p.manhattenDistance(x, y);
				}
				
				if (summedDistance < 10000) {
					totalCoveredArea += 1;
				}
			}
		}
		
		System.out.println("Stage 2: " + totalCoveredArea);
	}

	public static void main(String[] args) {
		new Day06().run();
	}

	private class Point {
		private final int pointId = Day06.pointId++;
		private final int x;
		private final int y;
		
		private int coveredArea = 0;
		
		private Point(int x , int y) {
			this.x = x;
			this.y = y;
		}
		
		private Point(String[] args) {
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
}
