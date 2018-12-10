package com.github.thedwoon.aoc.day04;

import java.util.ArrayList;

public class GuardProfile extends ArrayList<GuardShiftProfile> {
		private static final long serialVersionUID = 684151248351318L;
		public final int guardId;
		
		public GuardProfile(int guardId) {
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
		
		public double[] countSleepings() {
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
		
		public int computeTimeSlept() {
			int timeSlept = 0;
			for (GuardShiftProfile profile : this) {
				timeSlept += profile.timeAsleep;
			}
			
			return timeSlept;
		}
	}