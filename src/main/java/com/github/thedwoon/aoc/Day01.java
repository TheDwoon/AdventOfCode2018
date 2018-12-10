package com.github.thedwoon.aoc;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public final class Day01 extends AbstractDay<List<Integer>> {
	
	public Day01() {
		super();
	}
	
	public static void main(String[] args) {
		new Day01().run();
	}
	
	@Override
	protected List<Integer> getInput() {
		return getLines().stream().map(Integer::parseInt).collect(Collectors.toList());
	}
	
	protected String runPart1(List<Integer> input) {
		return Integer.toString(input.stream().mapToInt(Integer::intValue).sum());
	}
	
	protected String runPart2(List<Integer> input) {
		HashSet<Integer> reachedFrequencies = new HashSet<>();
 		int frequency = 0;
 		
 		for (int i = 0; !reachedFrequencies.contains(frequency); i = (i + 1) % input.size()) {
 			reachedFrequencies.add(frequency);
 			frequency += input.get(i);
 		}
 		
 		return Integer.toString(frequency);
	}
}
