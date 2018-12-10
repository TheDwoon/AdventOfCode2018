package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.thedwoon.aoc.day03.Rectangle;

public final class Day03 extends AbstractDay<List<Rectangle>>{

	public Day03() {
		super();
	}
	
	public static void main(String[] args) {
		new Day03().run();
	}
	
	@Override
	protected List<Rectangle> getInput() {
		return getLines().stream().map(Rectangle::new).collect(Collectors.toList());
	}
	
	protected String runPart1(List<Rectangle> input) {
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
		return Long.toString(count);
	}
	
	@Override
	protected String runPart2(List<Rectangle> input) {
		int id = (int) input.stream().filter(r -> !r.overlap(input)).mapToInt(Rectangle::getId).findFirst().getAsInt();
		return Integer.toString(id);
	}
	
	private static class RectList extends ArrayList<Rectangle> {
		private static final long serialVersionUID = -2675597112374764695L;
	}
}
