package com.github.thedwoon.aoc.day04;

public class WakeUpEvent extends Event {
	public WakeUpEvent(String line) {
		super(line);
	}
	
	@Override
	public GuardState getNewState() {
		return GuardState.AWAKE;
	}
}