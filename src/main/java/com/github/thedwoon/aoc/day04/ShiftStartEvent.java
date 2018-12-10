package com.github.thedwoon.aoc.day04;

import java.util.regex.Matcher;

import com.github.thedwoon.aoc.Day04;

public class ShiftStartEvent extends Event {
	public final int guardId;
	
	public ShiftStartEvent(String line) {
		super(line);
		
		Matcher m = Day04.GUARD_PATTERN.matcher(line);
		if (!m.find())
			throw new IllegalArgumentException();
		
		this.guardId = Integer.parseInt(m.group(1));
	}
	
	@Override
	public GuardState getNewState() {
		throw new UnsupportedOperationException();
	}
}