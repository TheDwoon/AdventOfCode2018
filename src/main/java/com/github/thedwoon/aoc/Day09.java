package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day09 extends AbstractDay<int[]> {
	private static final Pattern PATTERN = Pattern.compile("(\\d+) players; last marble is worth (\\d+) points");
	
	@Override
	protected int[] getInput() {
		Matcher m = PATTERN.matcher(getLines().get(0));
		if (!m.find())
			throw new IllegalArgumentException(getLines().get(0));
		
		return new int[] { Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))};
	}
	
	@Override
	protected String runPart1(int[] input) {
		return playGame(input[0], input[1]);
	}
	
	@Override
	protected String runPart2(int[] input) {
		return playGame(input[0], input[1] * 100);
	}
	
	private String playGame(int players, int rounds) {
		long[] playerScores = new long[players];
		Game g = new Game();
		
		for (int marble = 1; marble <= rounds; marble++) {
			if (marble % (rounds / 10) == 0)
				System.out.printf("PROGRESS: %.2f\n", marble / (double) rounds);
			int player = (marble - 1) % playerScores.length; 
			playerScores[player] += g.placeMarble(marble);
//			System.out.println(g);
		}
		
		long maxScore = playerScores[0];
		int bestPlayer = 0;
		for (int p = 0; p < playerScores.length; p++) {
			if (playerScores[p] > maxScore) {
				bestPlayer = p;
				maxScore = playerScores[p];
			}
			
			System.out.printf("Score for Player %d: %d\n", p + 1, playerScores[p]);
		}
		
		System.out.printf("***** WINNER *****\nPlayer %d: %d\n", bestPlayer + 1, maxScore);
		return Long.toString(maxScore);
	}
	
	public static void main(String[] args) {		
		new Day09().run();
	}

	private class Game {
		private final List<Integer> marbles = new ArrayList<>(3_500_000);
		private int currentMarbleIndex = 0;
		
		private Game() {
			marbles.add(0);
		}
		
		public int placeMarble(int marbleNumber) {
			if (marbleNumber % 23 == 0) {
				currentMarbleIndex -= 7;
				while (currentMarbleIndex < 0)
					currentMarbleIndex += marbles.size();
				
				int marbleRemoved = marbles.remove(currentMarbleIndex);
				currentMarbleIndex %= marbles.size();
				
				return marbleNumber + marbleRemoved;
			} else {
				int index1R = (currentMarbleIndex + 1) % marbles.size();
				int index2R = (currentMarbleIndex + 2) % marbles.size();
				
				int insertionPoint;
				if (index1R == 0 && index2R == 0) {
					// first element, simply append
					insertionPoint = marbles.size();
				} else if (index1R == marbles.size() - 1 && index2R == 0) {
					// append at the end
					insertionPoint = marbles.size();
				} else {
					insertionPoint = index2R;
				}
				
				marbles.add(insertionPoint, marbleNumber);
				currentMarbleIndex = insertionPoint;
				
				return 0;
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append('[');
			int i = 0;
			for (int marble : marbles) {
				if (i == currentMarbleIndex) {
					sb.append('(');
				}
				
				sb.append(Integer.toString(marble));
				
				if (i == currentMarbleIndex) {
					sb.append(')');
				}
				
				if (i < marbles.size() - 1)
					sb.append(' ');
				
				i++;
			}
			
			sb.append(']');
			
			return sb.toString();
		}
	}
}
