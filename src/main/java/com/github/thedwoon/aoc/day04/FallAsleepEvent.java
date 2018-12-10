package com.github.thedwoon.aoc.day04;

public class FallAsleepEvent extends Event {
	public FallAsleepEvent(String line) {
		super(line);
	}
	
	@Override
	public GuardState getNewState() {
		return GuardState.ASLEEP;
	}
}