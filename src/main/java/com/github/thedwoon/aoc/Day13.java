package com.github.thedwoon.aoc;

import java.util.Collections;
import java.util.List;

import com.github.thedwoon.aoc.day13.Minecart;
import com.github.thedwoon.aoc.day13.Minecart.Facing;
import com.github.thedwoon.aoc.day13.Track;
import com.github.thedwoon.aoc.day13.Track.AbstractRail;

public final class Day13 extends AbstractDay<Track> {

		public Day13() {
			super();
		}

		public static void main(String[] args) {
			new Day13().run();
		}
		
		@Override
		protected Track getInput() {
			List<String> lines = getLines();
			
			final int width = lines.get(0).length();
			final int height = lines.size();			
			Track track = new Track(width, height);
			for (int y = 0; y < height; y++) {
				String line = lines.get(y);
				for (int x = 0; x < width; x++) {
					char c = line.charAt(x);
					track.setRail(x, y, AbstractRail.parseRail(c));
					
					if (Facing.isFacing(c)) {
						Facing facing = Facing.fromCharacter(c);
						Minecart minecart = new Minecart(facing, x, y);
						track.addMinecart(minecart);
					}
				}
			}
			
			return track;
		}

		@Override
		protected String runPart1(Track track) {
			boolean collision = false;
			do {
				List<Minecart> minecarts = track.getMinecarts();
				Collections.sort(minecarts);
				
				for (Minecart minecart : minecarts) {
					AbstractRail rail = minecart.getRail(track);
					collision = !rail.manipulateMinecart(minecart);
					if (collision) {
						return String.format("%d,%d", minecart.getX(), minecart.getY());
					}
				}
			} while (!collision);
			
			return "<error>";
		}

		@Override
		protected String runPart2(Track track) {
			List<Minecart> minecarts = null;
			do { 
				minecarts = track.getMinecarts();
				Collections.sort(minecarts);
				
				for (Minecart minecart : minecarts) {
					AbstractRail rail = minecart.getRail(track);
					rail.manipulateMinecart(minecart);
				}
			} while (track.getMinecarts().size() > 1);
			
			Minecart minecart = minecarts.get(0);
			
			return String.format("%d,%d", minecart.getX(), minecart.getY());
		}
}
