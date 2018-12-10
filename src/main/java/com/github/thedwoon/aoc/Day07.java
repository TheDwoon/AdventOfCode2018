package com.github.thedwoon.aoc;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.thedwoon.aoc.day07.Node;
import com.github.thedwoon.aoc.day07.Worker;

public final class Day07 extends AbstractDay<Map<Character, Node>> {
	private static final Pattern PATTERN = Pattern.compile("Step ([A-Z]{1}) must be finished before step ([A-Z]{1}) can begin.");
	
	public Day07() {
		super();
	}
	
	public static void main(String[] args) {
		new Day07().run();
	}

	@Override
	protected Map<Character, Node> getInput() {
		Map<Character, Node> nodes = new HashMap<>();
		
		for (String line : getLines()) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find())
				throw new IllegalArgumentException(line);
			
			char requiredNodeName = m.group(1).charAt(0);
			char nodeName = m.group(2).charAt(0);
			
			Node node = getNodeFromMap(nodes, nodeName);
			Node requiredNode = getNodeFromMap(nodes, requiredNodeName);
			node.requirements.add(requiredNode);
			requiredNode.following.add(node);
		}
		
		return nodes;
	}
	
	@Override
	protected String runPart1(Map<Character, Node> input) {
		input.values().forEach(System.out::println);
		
		StringBuilder sb = new StringBuilder();
		List<Node> visitNodes = input.values().stream().collect(Collectors.toList());
		Collections.sort(visitNodes);
		while (!visitNodes.isEmpty() && visitNodes.get(0).requirements.size() == 0) {
			Node n = visitNodes.remove(0);
			
			n.following.forEach(f -> f.requirements.remove(n));

			sb.append(n.name);
			
			Collections.sort(visitNodes);
		}
		
		return sb.toString();
	}
	
	@Override
	protected String runPart2(Map<Character, Node> input) {
		Worker[] workers = new Worker[] {new Worker(), new Worker(), new Worker(), new Worker(), new Worker()};
		
		int totalTime = 0;
		StringBuilder sb = new StringBuilder();
		List<Node> visitNodes = input.values().stream().collect(Collectors.toList());
		Collections.sort(visitNodes);
		while (!visitNodes.isEmpty()) {
			while (!visitNodes.isEmpty() && visitNodes.get(0).requirements.size() == 0 && timeToNextWorker(workers) == 0) {
				Node n = visitNodes.remove(0);
				Worker w = nextWorker(workers);
				w.setWorkload(n);
			}
			
			int dt = Arrays.stream(workers).filter(Worker::isBusy).mapToInt(Worker::getTimeRemaining).min().orElse(0);
			totalTime += dt;
			passTime(workers, dt, sb);
			Collections.sort(visitNodes);
		}
		
		totalTime += Arrays.stream(workers).mapToInt(Worker::getTimeRemaining).max().orElse(0);
		return Integer.toString(totalTime);
	}
	
	private int timeToNextWorker(Worker[] workers) {
		return Arrays.stream(workers).mapToInt(Worker::getTimeRemaining).min().orElse(0);
	}

	private Worker nextWorker(Worker[] workers) {
		Worker w = workers[0];
		for (int i = 1; i < workers.length; i++) {
			if (workers[i].getTimeRemaining() < w.getTimeRemaining()) 
				w = workers[i];
		}
		
		return w;
	}
	
	private void passTime(Worker[] workers, int time, StringBuilder sb) {
		for (int i = 0; i < workers.length; i++) {
			workers[i].passTime(time);
			if (workers[i].getTimeRemaining() == 0 && workers[i].currentNode != null) {
				workers[i].finish(sb);
			}
		}
	}
	
	public static int getTimeForStep(char c) {
		return 60 + (c - 'A') + 1;
	}
	
	public static Node getNodeFromMap(Map<Character, Node> nodes, char nodeName) {
		Node node = nodes.get(nodeName);
		if (node == null) {
			node = new Node(nodeName);
			nodes.put(nodeName, node);
		}
		
		return node;
	}
}
