package com.github.thedwoon.aoc.day15;

import java.util.Objects;

public final class Tile {
	private final Board board;
	private final int x;
	private final int y;
	
	private Material material;
	private Unit unit;
	
	public Tile(Board board, int x, int y, Material material) {
		this.board = Objects.requireNonNull(board);
		this.x = x;
		this.y = y;
		this.material = material;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public void setUnit(Unit unit) {
		if (this.unit != unit) {
			if (this.unit != null) {
				board.removeUnit(this.unit);
			} 
			if (unit != null) {
				unit.setX(x);
				unit.setY(y);
				board.addUnit(unit);
			}
		}
		
		this.unit = unit;
	}
	
	public char getSymbol() {
		if (unit == null)
			return material.getSymbol();
		else
			return unit.getSymbol();
	}
	
	public static enum Material {
		WALL('#', false),
		CAVERN('.', true);
		
		private final char symbol;
		private final boolean walkable;
		
		private Material(char symbol, boolean walkable) {
			this.symbol = symbol;
			this.walkable = walkable;
		}
		
		public static Material fromChar(char c) {
			switch (c) {
			case '#':
				return WALL;
			case '.':
			case 'E':
			case 'G':
				return CAVERN;
			default:
				throw new IllegalArgumentException("Unkown material: " + c);	
			}
		}
		
		public char getSymbol() {
			return symbol;
		}
		
		public boolean isWalkable() {
			return walkable;
		}
	}
}
