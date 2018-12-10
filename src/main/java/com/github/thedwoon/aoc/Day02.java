package com.github.thedwoon.aoc;

import java.util.List;

public final class Day02 extends AbstractDay<List<String>> {
	
	public Day02() {
		super();
	}
	
	public static void main(String[] args) {
		new Day02().run();
	}
	
	@Override
	protected List<String> getInput() {
		return getLines();
	}

	protected String runPart1(List<String> lines) {
		int twos = 0;
		int threes = 0;
		
		for (String line : lines) {
			char[] chars = line.toCharArray();
			int[] h = createHistogramm(chars);
			if (containsTwo(h))
				twos += 1;
			
			if (containsThree(h))
				threes += 1;
		}
		
		return Integer.toString(twos * threes);
	}
	
	protected String runPart2(List<String> lines) {
		for (String in1 : lines) {
			char[] a = in1.toCharArray();
			for (String in2 : lines) {
				char[] b = in2.toCharArray();
				int d = computeDistance(a, b);
				if (d == 1) {					
					return filterEquals(a, b);
				}
			}
		}
		
		return "No solution :(";
	}

	private static int[] createHistogramm(char[] chars) {
		int[] h = new int[26];
		for (char c : chars) {
			h[c - 'a'] += 1;
		}
		
		return h;
	}
	
	private static boolean containsTwo(int[] h) {
		for (int i : h) {
			if (i == 2) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean containsThree(int[] h) {
		for (int i : h) {
			if (i == 3) {
				return true;
			}
		}
		
		return false;
	}
	
	private static int computeDistance(char[] a, char[] b) {
		int dist = 0;
		
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i])
				dist += 1;
		}
		
		return dist;
	}
	
	private static String filterEquals(char[] a, char[] b) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < a.length; i++) {
			if (a[i] == b[i])
				sb.append(a[i]);
		}
		
		return sb.toString();
	}
}
