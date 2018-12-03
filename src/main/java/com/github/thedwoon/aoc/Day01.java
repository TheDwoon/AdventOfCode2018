package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day01 {
	public static void main(String[] args) {
		ArrayList<Integer> input = new ArrayList<>();
		try (Scanner scanner = new Scanner(Day01.class.getResourceAsStream("Day01.txt"))) {
			while (scanner.hasNextInt()) {
				input.add(scanner.nextInt());
			}
		}
		
		stage1(input);
		stage2(input);
	}
	
	private static void stage1(ArrayList<Integer> input) {
		int frequency = input.stream().mapToInt(Integer::intValue).sum();
		
		System.out.println("Stage 1: " + frequency);
	}
	
	private static void stage2(ArrayList<Integer> input) {
		HashSet<Integer> reachedFrequencies = new HashSet<>();
 		int frequency = 0;
 		
 		for (int i = 0; !reachedFrequencies.contains(frequency); i = (i + 1) % input.size()) {
 			reachedFrequencies.add(frequency);
 			frequency += input.get(i);
 		}
 		
 		System.out.println("Stage 2: " + frequency);
	}
}
