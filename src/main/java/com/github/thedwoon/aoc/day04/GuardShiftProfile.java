package com.github.thedwoon.aoc.day04;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GuardShiftProfile {
	public final int guardId;
	
	final int timeAsleep;
	final GuardState[] states = new GuardState[60];
	
	public GuardShiftProfile(ShiftStartEvent e1, List<Event> events) {
		this.guardId = e1.guardId;
		
		Iterator<Event> it = events.iterator();
		Event e = null;
		GuardState currentState = GuardState.AWAKE;
		
		for (int min = 0; min < 60; min++) {
			if (e == null && it.hasNext())
				e = it.next();
			
			if (e != null && min == e.minute) {
				currentState = e.getNewState();
				e = null;
			}
			
			states[min] = currentState;
		}
		
		timeAsleep = (int) Arrays.stream(states).filter(GuardState.ASLEEP::equals).count();
	}		
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Guard %4d: ", guardId));
		for (int i = 0; i < states.length; i++) {
			if (states[i] == GuardState.AWAKE)
				sb.append('.');
			else
				sb.append('#');
		}
		
		return sb.toString();
	}
}