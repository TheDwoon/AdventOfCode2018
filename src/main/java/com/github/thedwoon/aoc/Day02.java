package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day02 {
	public static void main(String[] args) {
		List<String> lines = new ArrayList<>();
		try (Scanner scanner = new Scanner(Day02.class.getResourceAsStream("/Day02.txt"))) {
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
		}
		
		stage1(lines);
		stage2(lines);
	}
	
	private static void stage1(List<String> lines) {
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
		
		System.out.println("Stage 1: " + twos * threes);
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
	
	private static void stage2(List<String> lines) {
		for (String in1 : lines) {
			char[] a = in1.toCharArray();
			for (String in2 : lines) {
				char[] b = in2.toCharArray();
				int d = computeDistance(a, b);
				if (d == 1) {					
					System.out.println("Stage 2: " + filterEquals(a, b));
					return;
				}
			}
		}
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
