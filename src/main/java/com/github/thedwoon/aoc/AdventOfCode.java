package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.List;

public final class AdventOfCode {
	private static final List<AbstractDay<?>> DAYS = new ArrayList<>();
	
	static {
		DAYS.add(new Day01());
		DAYS.add(new Day02());
		DAYS.add(new Day03());
		DAYS.add(new Day04());
		DAYS.add(new Day05());
		DAYS.add(new Day06());
		DAYS.add(new Day07());
		DAYS.add(new Day08());
		DAYS.add(new Day09());
		DAYS.add(new Day10());
		DAYS.add(new Day11());
		DAYS.add(new Day12());
	}
	
	private AdventOfCode() {
		
	}
	
	public static void main(String[] args) {
		DAYS.forEach(AbstractDay::run);
	}
}
