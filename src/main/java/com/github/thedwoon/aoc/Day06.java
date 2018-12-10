package com.github.thedwoon.aoc;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.thedwoon.aoc.day06.Point;

public final class Day06 extends AbstractDay<List<Point>> {
	public static int pointId = 0;
	
	public Day06() {
		super();
	}
	
	public static void main(String[] args) {
		new Day06().run();
	}

	@Override
	protected List<Point> getInput() {
		return getLines().stream().map(line -> line.split(", ")).map(Point::new).collect(Collectors.toList());
	}
	
	@Override
	protected String runPart1(List<Point> input) {
		final int minX = input.stream().mapToInt(Point::getX).min().orElse(0);
		final int maxX = input.stream().mapToInt(Point::getX).max().orElse(0);
		final int minY = input.stream().mapToInt(Point::getY).min().orElse(0);
		final int maxY = input.stream().mapToInt(Point::getY).max().orElse(0);
		final int width = maxX + 1 - minX;
		final int height = maxY + 1 - minY;
	
		Point[][] board = new Point[width][height];
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				int bestDistance = Integer.MAX_VALUE;
				Point bestPoint = null;
				for (Point p : input) {
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
				input.remove(board[i][0]);
				input.remove(board[i][height - 1]);
			}
			
			if (i < height) {
				input.remove(board[0][i]);
				input.remove(board[width - 1][i]);
			}
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Point p = board[i][j];				
				if (p != null)
					if (!input.contains(p)) 
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
		
		
		Collections.sort(input, (o1, o2) -> -Integer.compare(o1.coveredArea, o2.coveredArea));
		return Integer.toString(input.get(0).coveredArea);
	}
	
	@Override
	protected String runPart2(List<Point> input) {
		final int minX = input.stream().mapToInt(Point::getX).min().orElse(0);
		final int maxX = input.stream().mapToInt(Point::getX).max().orElse(0);
		final int minY = input.stream().mapToInt(Point::getY).min().orElse(0);
		final int maxY = input.stream().mapToInt(Point::getY).max().orElse(0);
		
		int totalCoveredArea = 0;
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				int summedDistance = 0;
				for (Point p : input) {
					summedDistance += p.manhattenDistance(x, y);
				}
				
				if (summedDistance < 10000) {
					totalCoveredArea += 1;
				}
			}
		}
		
		return Integer.toString(totalCoveredArea);
	}
}
