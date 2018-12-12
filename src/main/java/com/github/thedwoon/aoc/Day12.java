package com.github.thedwoon.aoc;

import java.util.List;
import java.util.stream.Collectors;

public class Day12 extends AbstractDay<List<String>> {
	private static final char PLANT = '#';
	private static final char EMPTY = '.';
	
	public Day12() {
		super();
	}
	
	public static void main(String[] args) {
		new Day12().run();
	}

	@Override
	protected String runPart1(List<String> input) {
		char[] gen = getInitialState(input);
		char[][] patterns = getPatterns(input);
		
//		printGeneration(gen);
		for (int i = 0; i < 20; i++) {
			gen = performGeneration(gen, patterns);
//			printGeneration(gen);
		}
	
		int sum = 0;
		for (int i = 0; i < gen.length; i++) {
			if (gen[i] == PLANT) {
				sum += i - 40;
			}
		}
		
		return Integer.toString(sum);
	}
	
	@Override
	protected String runPart2(List<String> input) {
		char[] gen = getInitialState(input);
		char[][] patterns = getPatterns(input);
		
		int[] sums = new int[100];
		int[] derivate = new int[sums.length];
		int i = 0;
		int sum = 0;
		do {
			gen = performGeneration(gen, patterns);
			int s = i % sums.length;
			int d = s == 0 ? sums.length - 1 : s - 1;
			sums[s] = sumIndex(gen, i++);
			derivate[s] = sums[s] - sums[d];
			sum += derivate[s];
		} while (!hasConverged(derivate));
	
		return Long.toString(sum + derivate[0] * (50000000000L - i - 2));
	}
	
	private boolean hasConverged(int[] conv) {
		for (int i = 0; i < conv.length - 1; i++) {
			if (conv[i] != conv[i + 1])
				return false;
		}
		
		return true;
	}
	
	private int sumIndex(char[] gen, int genCount) {
		int sum = 0;
		for (int i = 0; i < gen.length; i++) {
			if (gen[i] == PLANT) {
				sum += i - genCount * 2;
			}
		}
		
		return sum;
	}
	
	@Override
	protected List<String> getInput() {
		return getLines();
	}
	
	private char[] performGeneration(char[] pots, char[][] patterns) {
		char[] nextGeneration = new char[pots.length + 4];
		for (int i = 0; i < nextGeneration.length; i++) {
			nextGeneration[i] = EMPTY;
		}
		
		for (int i = -2; i < pots.length + 2; i++) {
			char[] p = new char[5];
			for (int j = 0; j < 5; j++) {
				if (j + i - 2 < 0 || j + i - 2 >= pots.length)
					p[j] = EMPTY;
				else
					p[j] = pots[i + j - 2];
					
			}
			
			for (int j = 0; j < patterns.length; j++) {			
				if (p[0] == patterns[j][0] && p[1] == patterns[j][1] && p[2] == patterns[j][2] && p[3] == patterns[j][3]
						&& p[4] == patterns[j][4]) {
					nextGeneration[i + 2] = patterns[j][5];
					break;
				}
			}
		}
		
		return nextGeneration;
	}
	
	private char[] getInitialState(List<String> input) {
		String line = input.stream().filter(l -> l.startsWith("initial state: ")).findFirst().get();
		line = line.substring("initial state: ".length());
		
		return line.trim().toCharArray();
	}
	
	private char[][] getPatterns(List<String> input) {
		List<char[]> patterns = input.stream().filter(l -> l.contains(" => ")).map(l -> l.replace(" => ", "")).map(String::toCharArray).collect(Collectors.toList());
		char[][] p = new char[patterns.size()][6];
		for (int i = 0; i < patterns.size(); i++) {
			char[] c = patterns.get(i);
			for (int j = 0; j < 6; j++) {
				p[i][j] = c[j];
			}
		}
		
		return p;
	}
}
