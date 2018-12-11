package com.github.thedwoon.aoc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class AbstractDay<T> implements Runnable {
	private final String resource;	
	
	public AbstractDay() {
		this.resource = "/" + getClass().getSimpleName() + ".txt";
	}
	
	public AbstractDay(String resource) {
		this.resource = resource;		
	}
	
	public final InputStream getResourceAsStream() {
		return getClass().getResourceAsStream(resource);
	}
	
	public final List<String> getLines() {
		List<String> lines = new ArrayList<>();
		
		try (Scanner scanner = new Scanner(getResourceAsStream())) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line == null || line.isEmpty())
					continue;
				
				lines.add(line);
			}
		}
		
		return lines;
	}
	
	@Override
	public final void run() {
		final long t1 = System.nanoTime();
		T input = getInput();
		final long t2 = System.nanoTime();
		String resultPart1 = runPart1(input);
		final long t3 = System.nanoTime();
		
		final long t4 = System.nanoTime();
		input = getInput();
		final long t5 = System.nanoTime();
		String resultPart2 = runPart2(input);
		final long t6 = System.nanoTime();
		
		System.out.println("*********************************************************");
		System.out.println("  " + getClass().getSimpleName() + " completed!");
		System.out.println("> Part 1: " + resultPart1);
		System.out.printf("> Input: %s, Part 1: %s, Total: %s\n", formatTime(t2 - t1), formatTime(t3 - t2), formatTime(t3 - t1));
		System.out.println();
		System.out.println("> Part 2: " + resultPart2);
		System.out.printf("> Input: %s, Part 2: %s, Total: %s\n", formatTime(t5 - t4), formatTime(t6 - t5), formatTime(t6 - t4));
		System.out.println("*********************************************************");
	}	
	
	private final String formatTime(long timeDelta) {
		final TimeUnit t = TimeUnit.NANOSECONDS;
		if (timeDelta < 1000) {
			return String.format("%6d ns", timeDelta);
		} else if (timeDelta < 1_000_000) {
			return String.format("%3.2f µs", timeDelta / 1_000d);		
		} else if (timeDelta < 1_000_000_000) {
			return String.format("%3.2f ms", timeDelta / 1_000_000d);
		} else if (t.toSeconds(timeDelta) < 180) {
			return String.format("%3.2f s", timeDelta / 1_000_000_000d);
		} else if (t.toMinutes(timeDelta) < 180) {
			return String.format("%3.2f min", timeDelta / 60_000_000_000d);
		} else {
			return String.format("%3.2f h", timeDelta / 3_600_000_000_000d);
		}
	}
	
	protected abstract T getInput();
	protected abstract String runPart1(T input);
	protected abstract String runPart2(T input);
	
}
