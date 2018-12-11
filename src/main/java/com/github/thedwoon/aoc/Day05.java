package com.github.thedwoon.aoc;

import java.util.Arrays;

public final class Day05 extends AbstractDay<String> {
	private static final char NONE = '-';
	
	public Day05() {
		super();
	}
	
	public static void main(String[] args) {
		new Day05().run();
	}

	@Override
	protected String getInput() {
		return getLines().get(0);
	}
	
	@Override
	protected String runPart1(String input) {
		return Integer.toString(react(input));
	}
	
	@Override
	protected String runPart2(String input) {
		char[] alphabet = "abcdefghijklmopqrstuvwxyz".toCharArray();
		int[] resultingLength = new int[alphabet.length];
		
		for (int i = 0; i < alphabet.length; i++) {
			String lower = new String( new char[] { Character.toLowerCase(alphabet[i]) });
			String upper = new String( new char[] { Character.toUpperCase(alphabet[i]) });
			String modified = input.replace(lower, "").replace(upper, "");
			resultingLength[i] = react(modified);
		}
		
		return Integer.toString(Arrays.stream(resultingLength).min().orElse(0));
	}
	
	private int react(String input) {
		char[] buffer = input.toCharArray();
		int currentIndex = 0;
		int currentNext = 1;
		int size = buffer.length;
		
		while (currentNext != -1) {
			char a = buffer[currentIndex];
			char b = buffer[currentNext];
			if (Character.toLowerCase(a) == Character.toLowerCase(b) && a != b) {
				buffer[currentIndex] = NONE;
				buffer[currentNext] = NONE;
				size -= 2;
				
				int prevIndex = prevIndex(buffer, currentIndex);
				currentIndex = prevIndex != -1 ? prevIndex : nextIndex(buffer, currentIndex);
				currentNext = nextIndex(buffer, currentNext);
			} else {
				currentIndex = currentNext;
				currentNext = nextIndex(buffer, currentNext);
			}
		}
		
		return size;
	}
	
	private int nextIndex(char[] buffer, int currentIndex) {
		while (++currentIndex < buffer.length && buffer[currentIndex] == NONE);
		
		return currentIndex < buffer.length ? currentIndex : -1;
	}
	
	private int prevIndex(char[] buffer, int currentIndex) {
		while (--currentIndex >= 0 && buffer[currentIndex] == NONE);
		
		return currentIndex >= 0 ? currentIndex : -1;
	}
}
