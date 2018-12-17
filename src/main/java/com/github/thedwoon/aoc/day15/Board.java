package com.github.thedwoon.aoc.day15;

import java.util.ArrayList;
import java.util.List;

import com.github.thedwoon.aoc.day15.Tile.Material;

public final class Board {
	private final int width;
	private final int height;
	private final Tile[][] tiles;
	private final List<Unit> units;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = new Tile(this, x, y, Material.CAVERN);
			}
		}
		units = new ArrayList<>();
	}
	
	public List<Unit> getUnits() {
		return new ArrayList<>(units);
	}
	
	public void addUnit(Unit unit) {
		if (unit == null)
			return;
		
		units.add(unit);
	}
	
	public void removeUnit(Unit unit) {
		if (unit == null)
			return;
		
		units.remove(unit);
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public boolean hasTile(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sb.append(tiles[x][y].getSymbol());
			}
			
			sb.append('\n');
		}
		
		return sb.toString();
	}
}
