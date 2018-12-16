package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.List;

public final class Day14 extends AbstractDay<Integer> {

	public Day14() {
		super();
	}
	
	public static void main(String[] args) {
		new Day14().run();
	}

	@Override
	protected Integer getInput() {
		return Integer.parseInt(getLines().get(0));
	}

	@Override
	protected String runPart1(Integer trainingReceipts) {
		List<Integer> receipts = new ArrayList<>(trainingReceipts + 16);

		receipts.add(3);
		receipts.add(7);
		
		int firstElf = 0;
		int secondElf = 1;
		
		while (receipts.size() < trainingReceipts + 10) {
			final int receiptA = receipts.get(firstElf);
			final int receiptB = receipts.get(secondElf);			
			final int sum = receiptA + receiptB;
			if (sum < 10) {
				receipts.add(sum);
			} else {
				receipts.add(sum / 10);
				receipts.add(sum % 10);
			}
			
			final int size = receipts.size();
			firstElf = (firstElf + receiptA + 1) % size;
			secondElf = (secondElf + receiptB + 1) % size;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = trainingReceipts; i < trainingReceipts + 10; i++) {
			sb.append(Integer.toString(receipts.get(i)));
		}
		
		return sb.toString();
	}
	
	@Override
	protected String runPart2(Integer trainingReceipts) {
		char[] toMatch = Integer.toString(trainingReceipts).toCharArray();
		int matchIndex = 0;
		int receiptIndex = 0;
		
		StringBuilder receipts = new StringBuilder();

		receipts.append('3');
		receipts.append('7');
		
		int firstElf = 0;
		int secondElf = 1;
		
		while (matchIndex < toMatch.length) {
			final int receiptA = receipts.charAt(firstElf) - '0';
			final int receiptB = receipts.charAt(secondElf) - '0';			
			final int sum = receiptA + receiptB;

			if (sum < 10) {
				receipts.append(Integer.toString(sum).charAt(0));
			} else {
				receipts.append(Integer.toString(sum / 10).charAt(0));
				receipts.append(Integer.toString(sum % 10).charAt(0));
			}
			
			final int size = receipts.length();
			while (receiptIndex < size && matchIndex < toMatch.length) {
				if (toMatch[matchIndex] == receipts.charAt(receiptIndex++)) {
					matchIndex++;
				} else {
					matchIndex = 0;
				}
			}
			
			firstElf = (firstElf + receiptA + 1) % size;
			secondElf = (secondElf + receiptB + 1) % size;
		}
		
		return Integer.toString(receiptIndex - toMatch.length);
	}
}
