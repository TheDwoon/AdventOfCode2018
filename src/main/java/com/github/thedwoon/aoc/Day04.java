package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day04 extends AbstractDay {
	// [1518-07-29 00:22] falls asleep
	private static final Pattern DATE_PATTERN = Pattern.compile("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\]");
//	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd HH:mm]");
	// [1518-08-04 23:46] Guard #701 begins shift
	private static final Pattern GUARD_PATTERN = Pattern.compile("Guard #(\\d+) begins shift");
	
	public Day04() {
		super();
	}

	public static void main(String[] args) {
		new Day04().run();
	}
	
	@Override
	public void run() {
		// Stage 1
		List<Event> events = getLines().stream().map(this::convertToEvent).collect(Collectors.toList()); 
		Collections.sort(events);
		for (Event e : events) {
			System.out.println(e.line);
		}
		
		List<GuardShiftProfile> shiftProfiles = new ArrayList<>();
		
		ShiftStartEvent currentShift = null;
		List<Event> shiftEvents = null;
		for (Event e : events) {
			if (e instanceof ShiftStartEvent) {
				if (currentShift != null) {
					shiftProfiles.add(new GuardShiftProfile(currentShift, shiftEvents));
				}
				
				currentShift = (ShiftStartEvent) e;
				shiftEvents = new ArrayList<>();
			} else {
				shiftEvents.add(e);
			}
		}
		
		Map<Integer, GuardProfile> guardProfiles = new HashMap<>();
		for (GuardShiftProfile shiftProfile : shiftProfiles) {
			System.out.println(shiftProfile);
			
			GuardProfile guardProfile = guardProfiles.get(shiftProfile.guardId);
			if (guardProfile == null) {
				guardProfile = new GuardProfile(shiftProfile.guardId);
				guardProfiles.put(shiftProfile.guardId, guardProfile);
			}
			
			guardProfile.add(shiftProfile);
		}
		
		int mostTimeSlept = -1;
		GuardProfile bestGuardProfile = null;
		for (GuardProfile guardProfile : guardProfiles.values()) {
			int timeSlept = guardProfile.computeTimeSlept();
			if (mostTimeSlept < timeSlept) {
				mostTimeSlept = timeSlept;
				bestGuardProfile = guardProfile;
			}
			
			System.out.println(guardProfile);
		}
		
		int bestMinute = -1;
		double bestChance = -1;
		double[] chances = bestGuardProfile.countSleepings();
		for (int i = 0; i < chances.length; i++) {
			if (bestChance < chances[i]) {
				bestChance = chances[i];
				bestMinute = i;
			}
		}
		
		System.out.printf("Stage 1: Best local chance of %.2f at minute %d with guard %d. Code %d\n", 
				bestChance, bestGuardProfile.guardId, bestMinute, bestMinute * bestGuardProfile.guardId);
		
		bestMinute = -1;
		bestChance = -1;
		bestGuardProfile = null;
		for (GuardProfile guardProfile : guardProfiles.values()) {
			chances = guardProfile.countSleepings();
			for (int i = 0; i < chances.length; i++) {
				if (bestChance < chances[i]) {
					bestChance = chances[i];
					bestMinute = i;
					bestGuardProfile = guardProfile;
				}
			}
		}
		
		System.out.printf("Stage 2: Best global chance of %.2f at minute %d with guard %d. Code %d\n", 
				bestChance, bestGuardProfile.guardId, bestMinute, bestMinute * bestGuardProfile.guardId);
	}
	
	private Event convertToEvent(String line) {
		if (line.contains("begins shift"))
			return new ShiftStartEvent(line);
		else if (line.contains("falls asleep"))
			return new FallAsleepEvent(line);
		else if (line.contains("wakes up"))
			return new WakeUpEvent(line);
		
		throw new IllegalArgumentException(line);
	}
	
	private abstract class Event implements Comparable<Event> {		
		protected final String line;		
		protected final int year;
		protected final int month;
		protected final int day;
		protected final int hour;
		protected final int minute;
		
		private Event(String line) {
			this.line = line;
			
			Matcher m = DATE_PATTERN.matcher(line);
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
		
		protected abstract GuardState getNewState();
	}
	
	private class ShiftStartEvent extends Event {
		protected final int guardId;
		
		private ShiftStartEvent(String line) {
			super(line);
			
			Matcher m = GUARD_PATTERN.matcher(line);
			if (!m.find())
				throw new IllegalArgumentException();
			
			this.guardId = Integer.parseInt(m.group(1));
		}
		
		@Override
		protected GuardState getNewState() {
			throw new UnsupportedOperationException();
		}
	}
	
	private class WakeUpEvent extends Event {
		private WakeUpEvent(String line) {
			super(line);
		}
		
		@Override
		protected GuardState getNewState() {
			return GuardState.AWAKE;
		}
	}
	
	private class FallAsleepEvent extends Event {
		private FallAsleepEvent(String line) {
			super(line);
		}
		
		@Override
		protected GuardState getNewState() {
			return GuardState.ASLEEP;
		}
	}
	
	private class GuardShiftProfile {
		private final int guardId;
		private final ShiftStartEvent shiftStartEvent;
		private final List<Event> events;
		
		private final int timeAwake;
		private final int timeAsleep;
		private final GuardState[] states = new GuardState[60];
		
		private GuardShiftProfile(ShiftStartEvent e1, List<Event> events) {
			this.guardId = e1.guardId;
			this.shiftStartEvent = e1;
			this.events = events;
			
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
			
			timeAwake = (int) Arrays.stream(states).filter(GuardState.AWAKE::equals).count();
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
	
	private class GuardProfile extends ArrayList<GuardShiftProfile> {
		private final int guardId;
		
		private GuardProfile(int guardId) {
			super();
			
			this.guardId = guardId;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append(String.format("Guard %4d: ", guardId));
			
			double[] chances = countSleepings();
			for (int i = 0; i < chances.length; i++) {
				int c = (int) (chances[i] / size() * 10);
				sb.append(Integer.toString(c));
			}
			
			return sb.toString();
		}
		
		protected double[] countSleepings() {
			// chance of guard being asleep
			double[] chances = new double[60];
			
			for (GuardShiftProfile profile : this) {
				for (int i = 0; i < profile.states.length; i++) {
					if (profile.states[i] == GuardState.ASLEEP) {
						chances[i] += 1;
					}
				}
			}
			
//			if (size() > 0) {
//				for (int i = 0; i < chances.length; i++) {
//					chances[i] /= size();
//				}
//			}
			
			return chances;
		}
		
		protected int computeTimeSlept() {
			int timeSlept = 0;
			for (GuardShiftProfile profile : this) {
				timeSlept += profile.timeAsleep;
			}
			
			return timeSlept;
		}
	}
	
	private enum GuardState {
		AWAKE,
		ASLEEP;
	}
}
