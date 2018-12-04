package com.github.thedwoon.aoc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractDay {
	private final String resource;	
	
	public AbstractDay() {
		this.resource = "/" + getClass().getSimpleName() + ".txt";
	}
	
	public AbstractDay(String resource) {
		this.resource = resource;		
	}
	
	public InputStream getResourceAsStream() {
		return getClass().getResourceAsStream(resource);
	}
	
	public List<String> getLines() {
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
	
	public abstract void run();
}
