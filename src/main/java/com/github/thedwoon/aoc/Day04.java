package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.thedwoon.aoc.day04.Event;
import com.github.thedwoon.aoc.day04.FallAsleepEvent;
import com.github.thedwoon.aoc.day04.GuardProfile;
import com.github.thedwoon.aoc.day04.GuardShiftProfile;
import com.github.thedwoon.aoc.day04.ShiftStartEvent;
import com.github.thedwoon.aoc.day04.WakeUpEvent;

public final class Day04 extends AbstractDay<List<GuardProfile>> {
	// [1518-07-29 00:22] falls asleep
	public static final Pattern DATE_PATTERN = Pattern.compile("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\]");
//	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd HH:mm]");
	// [1518-08-04 23:46] Guard #701 begins shift
	public static final Pattern GUARD_PATTERN = Pattern.compile("Guard #(\\d+) begins shift");
	
	public Day04() {
		super();
	}

	public static void main(String[] args) {
		new Day04().run();
	}
	
	@Override
	protected List<GuardProfile> getInput() {
		List<Event> events = getLines().stream().map(this::convertToEvent).collect(Collectors.toList()); 
		Collections.sort(events);

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
			GuardProfile guardProfile = guardProfiles.get(shiftProfile.guardId);
			if (guardProfile == null) {
				guardProfile = new GuardProfile(shiftProfile.guardId);
				guardProfiles.put(shiftProfile.guardId, guardProfile);
			}
			
			guardProfile.add(shiftProfile);
		}
		
		return new ArrayList<>(guardProfiles.values());
	}
	
	@Override
	protected String runPart1(List<GuardProfile> input) {
		int mostTimeSlept = -1;
		GuardProfile bestGuardProfile = null;
		for (GuardProfile guardProfile : input) {
			int timeSlept = guardProfile.computeTimeSlept();
			if (mostTimeSlept < timeSlept) {
				mostTimeSlept = timeSlept;
				bestGuardProfile = guardProfile;
			}
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
		
		return Integer.toString(bestMinute * bestGuardProfile.guardId);
	}
	
	@Override
	protected String runPart2(List<GuardProfile> input) {
		int bestMinute = -1;
		double bestChance = -1;
		GuardProfile bestGuardProfile = null;
		for (GuardProfile guardProfile : input) {
			double[] chances = guardProfile.countSleepings();
			for (int i = 0; i < chances.length; i++) {
				if (bestChance < chances[i]) {
					bestChance = chances[i];
					bestMinute = i;
					bestGuardProfile = guardProfile;
				}
			}
		}
		
		return Integer.toString(bestMinute * bestGuardProfile.guardId);
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
}
