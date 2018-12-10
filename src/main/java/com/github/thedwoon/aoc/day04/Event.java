package com.github.thedwoon.aoc.day04;

import java.util.Arrays;
import java.util.regex.Matcher;

import com.github.thedwoon.aoc.Day04;

public abstract class Event implements Comparable<Event> {		
	public final String line;		
	public final int year;
	public final int month;
	public final int day;
	public final int hour;
	public final int minute;
	
	public Event(String line) {
		this.line = line;
		
		Matcher m = Day04.DATE_PATTERN.matcher(line);
		if (!m.find())
			throw new IllegalArgumentException();
		
		this.year = Integer.parseInt(m.group(1));
		this.month = Integer.parseInt(m.group(2));
		this.day = Integer.parseInt(m.group(3));
		this.hour = Integer.parseInt(m.group(4));
		this.minute = Integer.parseInt(m.group(5));
	}

	@Override
	public int compareTo(Event o) {
		return compareHelper(Integer.compare(year, o.year), 
				Integer.compare(month, o.month), 
				Integer.compare(day, o.day),
				Integer.compare(hour, o.hour),
				Integer.compare(minute, o.minute));
	}
	
	private int compareHelper(int... compares) {
		return Arrays.stream(compares).filter(i -> i != 0).findFirst().orElse(0);
	}
	
	public abstract GuardState getNewState();
}