package com.github.thedwoon.aoc.day15;

import java.util.Objects;

public class Unit {
	private final Team team;
	
	private Board board = null;
	private int x = -1;
	private int y = -1;
	
	public Unit(Team team) {
		this.team = Objects.requireNonNull(team);
	}
	
	public Team getTeam() {
		return team;
	}
	
	public char getSymbol() {
		return team.getSymbol();
	}
	
	public Board getBoard() {
		return board;
	}
	
	protected void setBoard(Board board) {
		this.board = board;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public static enum Team {
		ELF('E'),
		GOBLIN('G');
		
		private final char symbol;
		
		private Team(char symbol) {
			this.symbol = symbol;
		}
		
		public char getSymbol() {
			return symbol;
		}
	}
}
