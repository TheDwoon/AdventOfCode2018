package com.github.thedwoon.aoc;

import java.util.List;

import com.github.thedwoon.aoc.day15.Board;
import com.github.thedwoon.aoc.day15.Tile;
import com.github.thedwoon.aoc.day15.Tile.Material;
import com.github.thedwoon.aoc.day15.Unit;
import com.github.thedwoon.aoc.day15.Unit.Team;

public final class Day15 extends AbstractDay<Board> {

	public Day15() {
		super();
	}
	
	public static void main(String[] args) {
		new Day15().run();
	}

	@Override
	protected Board getInput() {
		List<String> lines = getLines();
		final int width = lines.get(0).length();
		final int height = lines.size();
		Board board = new Board(width, height);
		for (int y = 0; y < height; y++) {
			char[] line = lines.get(y).toCharArray();
			for (int x = 0; x < width; x++) {
				final char c = line[x];
				final Tile tile = board.getTile(x, y);
				tile.setMaterial(Material.fromChar(c));
				
				Unit unit = null;
				if (c == 'G') {
					unit = new Unit(Team.GOBLIN);
				} else if (c == 'E') {
					unit = new Unit(Team.ELF);
				}

				tile.setUnit(unit);
			}
		}
		
		return board;
	}
	
	@Override
	protected String runPart1(Board board) {
		System.out.println(board);
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String runPart2(Board board) {
		// TODO Auto-generated method stub
		return null;
	}
}
