package com.github.thedwoon.aoc;

import java.util.Arrays;

public class Day05 extends AbstractDay {

	@Override
	public void run() {
		String input = getLines().get(0);			
				
		System.out.println("Stage 1: " + react(input).length());
		
		char[] alphabet = "abcdefghijklmopqrstuvwxyz".toCharArray();
		int[] resultingLength = new int[alphabet.length];
		
		for (int i = 0; i < alphabet.length; i++) {
			String lower = new String( new char[] { Character.toLowerCase(alphabet[i]) });
			String upper = new String( new char[] { Character.toUpperCase(alphabet[i]) });
			String modified = input.replace(lower, "").replace(upper, "");
			resultingLength[i] = react(modified).length();
		}
		
		System.out.println("Stage 2: " + Arrays.stream(resultingLength).min().orElse(0));
	}
	
	private String react(String input) {
		int size = 0;
		do {
			size = input.length();
			
			for (int i = 0; i < input.length() - 1; i++) {
				char a = input.charAt(i);
				char b = input.charAt(i + 1);
				
				if (Character.toLowerCase(a) == Character.toLowerCase(b) && a != b) {
					if (i + 2 < input.length()) {
						input = input.substring(0, i) + input.substring(i + 2);
					} else {
						input = input.substring(0, i);
					}
				}
			}
		} while (input.length() != size);
		
		return input;
	}

	public static void main(String[] args) {
		new Day05().run();
	}

}
