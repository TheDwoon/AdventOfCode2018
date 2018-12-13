package com.github.thedwoon.aoc.day13;

import java.util.ArrayList;
import java.util.List;

import com.github.thedwoon.aoc.day13.Minecart.Facing;

public final class Track {
	private final int width;
	private final int height;
	private final List<Minecart> minecarts = new ArrayList<>();
	private final AbstractRail[][] rails;
	
	public Track(int width, int height) {
		this.width = width;
		this.height = height;
		this.rails = new AbstractRail[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				rails[i][j] = new NoRail();
				rails[i][j].setTrack(this);
			}
		}
	}
	
	public List<Minecart> getMinecarts() {
		return new ArrayList<>(minecarts);
	}
	
	public void addMinecart(Minecart minecart) {
		minecarts.add(minecart);
		getRail(minecart.getX(), minecart.getY()).setMinecart(minecart);
	}
	
	public void removeMinecart(Minecart minecart) {
		getRail(minecart.getX(), minecart.getY()).clearMinecart();
		minecarts.remove(minecart);
	}
	
	public AbstractRail getRail(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return new NoRail();
		
		return rails[x][y];
	}
	
	public void setRail(int x, int y, AbstractRail rail) {
		rail.setMinecart(getRail(x, y).getMinecart());
		rail.setTrack(this);
		rails[x][y] = rail;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {				
				sb.append(rails[x][y].toString());
			}
			
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	public static abstract class AbstractRail {
		private Minecart minecart;
		private Track track;
				
		protected AbstractRail() {
			
		}		
		
		public static AbstractRail parseRail(char c) {
			switch (c) {
			case '/':
				return new RightTurn();
			case '\\':
				return new LeftTurn();
			case '^':
			case 'v':
			case '>':
			case '<':
			case '-':
			case '|':
				return new Straight();
			case '+':
				return new Crossing();
			default:
				return new NoRail();
			}
		}
		
		protected final Track getTrack() {
			return track;
		}
		
		protected final void setTrack(Track track) {
			this.track = track;
		}
		
		public final Minecart getMinecart() {
			return minecart;
		}
		
		public final void setMinecart(Minecart minecart) {
			this.minecart = minecart;
		}
		
		public final boolean hasMinecart() {
			return minecart != null;
		}
		
		public final void clearMinecart() {
			minecart = null;
		}
				
		public boolean manipulateMinecart(Minecart minecart) {
			int nx = minecart.getX() + minecart.getFacing().dx;
			int ny = minecart.getY() + minecart.getFacing().dy;
			
			AbstractRail currentRail = getTrack().getRail(minecart.getX(), minecart.getY());
			AbstractRail nextRail = getTrack().getRail(nx, ny);
			if (nextRail.hasMinecart()) {
				getTrack().removeMinecart(minecart);
				getTrack().removeMinecart(nextRail.getMinecart());
				minecart.setPosition(nx, ny);
				return false;
			}
			
			currentRail.clearMinecart();
			minecart.setPosition(nx, ny);
			nextRail.setMinecart(minecart);			
			return true;
		}
	}
	
	/** The direction of a turn is seen when driving from south to north. (\) */
	public static final class LeftTurn extends AbstractRail {
		protected LeftTurn() {
			super();
		}
		
		@Override
		public boolean manipulateMinecart(Minecart minecart) {
			Facing facing = minecart.getFacing();
			switch (minecart.getFacing()) {
			case NORTH:
			case SOUTH:
				minecart.setFacing(facing.left());
				break;
			case WEST:
			case EAST:
				minecart.setFacing(facing.right());
				break;
			default:
				throw new IllegalStateException("there shall be no other facing beside the default ones");
			}
			
			return super.manipulateMinecart(minecart);
		}
		
		@Override
		public String toString() {
			return "\\";
		}
	}
	
	/** The direction of a turn is seen when driving from south to north. (/) */
	public static final class RightTurn extends AbstractRail {
		
		protected RightTurn() {
			super();
		}
		
		@Override
		public boolean manipulateMinecart(Minecart minecart) {
			Facing facing = minecart.getFacing();
			switch (minecart.getFacing()) {
			case NORTH:
			case SOUTH:
				minecart.setFacing(facing.right());
				break;
			case WEST:
			case EAST:
				minecart.setFacing(facing.left());
				break;
			default:
				throw new IllegalStateException("there shall be no other facing beside the default ones");
			}
			
			return super.manipulateMinecart(minecart);
		}
		
		@Override
		public String toString() {
			return "/";
		}
	}
	
	public static final class Straight extends AbstractRail {
		protected Straight() {
			
		}
		
		@Override
		public String toString() {
			return "#";
		}
	}
	
	public static final class Crossing extends AbstractRail {
		protected Crossing() {
			
		}
		
		@Override
		public boolean manipulateMinecart(Minecart minecart) {
			minecart.turnForCrossing();
			
			return super.manipulateMinecart(minecart);
		}
		
		@Override
		public String toString() {
			return "+";
		}
	}
	
	public static final class NoRail extends AbstractRail {
		protected NoRail() {
			
		}
		
		@Override
		public boolean manipulateMinecart(Minecart minecart) {
			throw new IllegalStateException("Don't leave the track!");
		}
		
		@Override
		public String toString() {
			return " ";
		}
	}
}
