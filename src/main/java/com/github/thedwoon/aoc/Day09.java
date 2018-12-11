package com.github.thedwoon.aoc;

import java.util.Iterator;
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
//			if (marble % (rounds / 10) == 0)
//				System.out.printf("PROGRESS: %.2f\n", marble / (double) rounds);
			int player = (marble - 1) % playerScores.length; 
			playerScores[player] += g.placeMarble(marble);
		}
		
		long maxScore = playerScores[0];
//		int bestPlayer = 0;
		for (int p = 0; p < playerScores.length; p++) {
			if (playerScores[p] > maxScore) {
//				bestPlayer = p;
				maxScore = playerScores[p];
			}
			
//			System.out.printf("Score for Player %d: %d\n", p + 1, playerScores[p]);
		}
		
//		System.out.printf("***** WINNER *****\nPlayer %d: %d\n", bestPlayer + 1, maxScore);
		return Long.toString(maxScore);
	}
	
	public static void main(String[] args) {		
		new Day09().run();
	}

	private class Game {
		private final CircularList marbles = new CircularList();
		
		private Game() {

		}
		
		public int placeMarble(int marbleNumber) {
			if (marbleNumber % 23 == 0) {
				marbles.shiftLeft(7);
				int marbleRemoved = marbles.removeHead();
				
				return marbleNumber + marbleRemoved;
			} else {
				marbles.shiftRight(1);				
				marbles.addRight(marbleNumber);
				return 0;
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append('[');
			int i = 0;
			for (int marble : marbles) {
				if (i == 0) {
					sb.append('(');
				}
				
				sb.append(Integer.toString(marble));
				
				if (i == 0) {
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
	
	private static class CircularList implements Iterable<Integer> {
		private ListNode head;
		private int size = 1;
		
		private CircularList() {
			head = new ListNode(0);
			head.next = head;
			head.prev = head;
		}
		
		public void addRight(int value) {
			ListNode node = new ListNode(value);

			ListNode nextNode = head.next;
			ListNode prevNode = head;
			
			node.prev = prevNode;
			node.next = nextNode;
			
			nextNode.prev = node;
			prevNode.next = node;
			
			head = node;
			size += 1;
		}
		
		public void shiftRight(int amount) {
			for (int i = 0; i < amount; i++)
				head = head.next;
		}
		
		public void shiftLeft(int amount) {
			for (int i = 0; i < amount; i++)
				head = head.prev;
		}
		
		@SuppressWarnings("unused")
		public int getHead() {
			return head.value;
		}
		
		public int removeHead() {
			if (head.next == head)
				throw new IllegalStateException("one shall not remove the last element");
			
			ListNode removed = head;
			head = removed.next;
			
			removed.prev.next = removed.next;
			removed.next.prev = removed.prev;
			
			size -= 1;
			
			return removed.value;
		}
		
		public int size() {
			return size;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			final ListNode limiter = head;
			ListNode current = head;
			do {
				sb.append(current.value);
				if (current.next != limiter)
					sb.append(", ");
				
				current = current.next;
			} while (current != limiter);
			sb.append(']');
			
			return sb.toString();
		}
		
		@Override
		public Iterator<Integer> iterator() {
			return new Iterator<Integer>() {
				private final ListNode limiter = head;
				private ListNode current = null;
				
				@Override
				public boolean hasNext() {
					return current == null || current.next != limiter;
				}

				@Override
				public Integer next() {
					if (current == null)
						current = head;
					else
						current = current.next;
					
					return current.value;
				}
			};
		}
	}
	
	private static class ListNode {
		private final int value;

		private ListNode next;
		private ListNode prev;
	
		private ListNode(int value) {
			this.value = value;
		}
	}
}
