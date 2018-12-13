package com.github.thedwoon.aoc.day13;

import com.github.thedwoon.aoc.day13.Track.AbstractRail;

public final class Minecart implements Comparable<Minecart> {
	private Facing facing;
	private int x;
	private int y;
	
	private int thinkCounter = 0; 
	
	public Minecart(Facing facing, int x, int y) {
		this.facing = facing;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public AbstractRail getRail(Track track) {
		return track.getRail(x, y);
	}
	
	public Facing getFacing() {
		return facing;
	}
	
	public void setFacing(Facing facing) {
		this.facing = facing;
	}
	
	public void turnForCrossing() {
		switch (thinkCounter++ % 3) {
		case 0:
			facing = facing.left();
			break;
		case 1:
			// FULL STEAM AHEAD!
			break;
		case 2:
			facing = facing.right();
			break;
		default:
			throw new IllegalStateException("I cannot count that high");
		}
	}
	
	@Override
	public int compareTo(Minecart o) {
		int c = Integer.compare(x, o.x);
		if (c != 0)
			return c;
		else
			return Integer.compare(y, o.y);
	}
	
	public static enum Facing {
		NORTH(0, -1),
		SOUTH(0, 1),
		EAST(1, 0),
		WEST(-1, 0);
		
		static {
			NORTH.left = WEST;
			NORTH.right = EAST;
			
			EAST.left = NORTH;
			EAST.right = SOUTH;
			
			SOUTH.left = EAST;
			SOUTH.right = WEST;
			
			WEST.left = SOUTH;
			WEST.right = NORTH;
		}
		
		public final int dx;
		public final int dy;
		private Facing left;
		private Facing right;		
		
		private Facing(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
		
		public static Facing fromCharacter(char c) {
			switch (c) {
			case '<':
				return Facing.WEST;
			case '>':
				return Facing.EAST;
			case '^':
				return Facing.NORTH;
			case 'v':
				return Facing.SOUTH;
			default:
				throw new IllegalArgumentException("Not a facing: " + c);
			}
		}
		
		public static boolean isFacing(char c) {
			switch (c) {
			case '<':
			case '>':
			case '^':
			case 'v':
				return true;
			default:
				return false;
			}
		}
		
		public Facing left() {
			return left;
		}
		
		public Facing right() {
			return right;
		}
	}
}
