package com.github.thedwoon.aoc;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Day11 extends AbstractDay<Integer> {
	private static final int GRID_SIZE = 300;
	
	public Day11() {
		super();
	}
	
	public static void main(String[] args) {
		new Day11().run();
	}

	@Override
	protected Integer getInput() {
		return Integer.parseInt(getLines().get(0));
	}

	@Override
	protected String runPart1(Integer serial) {
		final int[][] grid = getPowerGrid(serial);
		int bestX = -1;
		int bestY = -1;
		int best = Integer.MIN_VALUE;
		for (int i = 0; i < GRID_SIZE - 3; i++) {
			for (int j = 0; j < GRID_SIZE - 3; j++) {
				int c = computeCluster(grid, i, j);
				if (c > best) {
					best = c;
					bestX = i;
					bestY = j;
				}
			}
		}
		
		return String.format("(%3d,%3d)", bestX + 1, bestY + 1);
	}

	@Override
	protected String runPart2(Integer serial) {
		final List<Future<SmartWitchSmart>> futures = new LinkedList<>();
		final int[][] grid = getPowerGrid(serial);
		final int[][] summedAreaTable = computeSummedAreaTable(grid);
				
		ExecutorService executorService = Executors.newFixedThreadPool(16);
		
		for (int x = 0; x < GRID_SIZE - 1; x++) {
			futures.add(executorService.submit(new SmartWitchSmart(summedAreaTable, x)));
		}
		
		SmartWitchSmart best = futures.stream().map(t -> {
			try {
				return t.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				return null;
			}
			}).max((o1, o2) -> Integer.compare(o1.sum, o2.sum)).orElse(null);
		
		executorService.shutdown();
		
		return best == null ? null : best.toString();
	}

	private static int computeCluster(int[][] grid, int x, int y) {
		int sum = 0;
		for (int i = x; i < x + 3; i++) {
			for (int j = y; j < y + 3; j++) {
				sum += grid[i][j];
			}
		}
		
		return sum;
	}

	private static int[][] computeSummedAreaTable(int[][] grid) {
		int[][] summedAreaTable = new int[GRID_SIZE][GRID_SIZE];
		
		for (int x = 0; x < GRID_SIZE; x++) {			
			for (int y = 0; y < GRID_SIZE; y++) {
				summedAreaTable[x][y] = grid[x][y];
				
				if (x > 0)
					summedAreaTable[x][y] += summedAreaTable[x - 1][y];
				if (y > 0) 
					summedAreaTable[x][y] += summedAreaTable[x][y - 1];
				if (x > 0 && y > 0) 
					summedAreaTable[x][y] -= summedAreaTable[x - 1][y - 1];
			}
		}
		
		return summedAreaTable;
	}
	
	private static int computeArea(int[][] summedAreaTable, int x, int y, int width) {
		int sum = 0;
		final int maxX = x + width;
		final int maxY = y + width;
		
		if (x > 0 && y > 0)
			sum += summedAreaTable[x - 1][y - 1];
		
		sum += summedAreaTable[maxX - 1][maxY - 1];
		
		if (x > 0) 
			sum -= summedAreaTable[x - 1][maxY - 1];
		if (y > 0)
			sum -= summedAreaTable[maxX - 1][y - 1];
		
		return sum;
	}
	
	private static int getPowerLevel(int x, int y, int serial) {
		final int rackId = x + 11;
		return (rackId * (y + 1) + serial) * rackId / 100 % 10 - 5;
	}
	
	private static int[][] getPowerGrid(int serial) {
		int[][] grid = new int[GRID_SIZE][GRID_SIZE];
		
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				grid[i][j] = getPowerLevel(i, j, serial);
			}
		}
		
		return grid;
	}
	
	public static class BurnWitchBurn implements Callable<BurnWitchBurn> {
		private final int[][] grid;
		private final int minX;
		private final int maxX;
		private final int minY;
		private final int maxY;
		
		private int sum = 0;
		
		private BurnWitchBurn(int[][] grid, int x, int y, int width) {
			this.grid = grid;
			this.minX = x;
			this.maxX = x + width;
			this.minY = y;
			this.maxY = y + width;
		}

		@Override
		public BurnWitchBurn call() throws Exception {
			for (int x = minX; x < maxX; x++) {
				for (int y = minY; y < maxY; y++) {
					sum += grid[x][y];
				}
			}
			
			return this;
		}
		
		@Override
		public String toString() {
			return String.format("(%3d,%3d): %d - %d", minX + 1, minY + 1, (maxX - minX), sum);
		}
	}
	
	public static class SmartWitchSmart implements Callable<SmartWitchSmart> {
		private final int[][] summedAreaTable;

		private final int x;
		private int y;		
		private int width;
		private int sum = 0;
		
		private SmartWitchSmart(int[][] summedAreaTable, int x) {
			this.summedAreaTable = summedAreaTable;
			this.x = x;
			this.y = 0;
		}

		@Override
		public SmartWitchSmart call() throws Exception {
			int bestArea = Integer.MIN_VALUE;
			int bestY = -1;
			int bestWidth = -1;
			for (int y = 0; y < GRID_SIZE - 1; y++) {
				for (int width = 1; width < GRID_SIZE - Math.max(this.x, y); width++) {
					int area = computeArea(summedAreaTable, this.x, y, width);
					if (area > bestArea) {
						bestArea = area;
						bestY = y;
						bestWidth = width;
					}
				}
			}
			
			this.y = bestY;
			this.width = bestWidth;
			this.sum = bestArea;
			
			return this;
		}
		
		@Override
		public String toString() {
			return String.format("(%3d,%3d): %d - %d", x + 1, y + 1, width, sum);
		}
	}
}
