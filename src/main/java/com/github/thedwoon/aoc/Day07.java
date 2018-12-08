package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 extends AbstractDay {
	private static final Pattern PATTERN = Pattern.compile("Step ([A-Z]{1}) must be finished before step ([A-Z]{1}) can begin.");
	
	@Override
	public void run() {
		Map<Character, Node> nodes = new HashMap<>();
		
		for (String line : getLines()) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find())
				throw new IllegalArgumentException(line);
			
			char nodeName = m.group(1).charAt(0);
			char requiredNodeName = m.group(2).charAt(0);
			
			Node node = getNodeFromMap(nodes, nodeName);
			Node requiredNode = getNodeFromMap(nodes, requiredNodeName);
			node.requirements.add(requiredNode);
			requiredNode.following.add(node);
		}
		
		nodes.values().forEach(System.out::println);
		
		StringBuilder sb = new StringBuilder();
		List<Node> visitNodes = nodes.values().stream().filter(n -> n.requirements.size() == 0).collect(Collectors.toList());
		Collections.sort(visitNodes);
		while (!visitNodes.isEmpty() && visitNodes.get(0).requirements.size() == 0) {
			Node n = visitNodes.remove(0);
			
			n.following.forEach(f -> f.requirements.remove(n));
			n.following.stream().filter(f -> f.requirements.size() == 0).forEach(visitNodes::add);

			sb.append(n.name);
			
			Collections.sort(visitNodes);
		}
		
		System.out.println("Stage 1: " + sb.toString());
	}

	private Node getNodeFromMap(Map<Character, Node> nodes, char nodeName) {
		Node node = nodes.get(nodeName);
		if (node == null) {
			node = new Node(nodeName);
			nodes.put(nodeName, node);
		}
		
		return node;
	}
	
	public static void main(String[] args) {
		new Day07().run();
	}
	
	private class Node implements Comparable<Node> {
		private List<Node> requirements = new ArrayList<>();
		private List<Node> following = new ArrayList<>();
		private char name;
		
		private Node(char name) {
			this.name = name;
		}
		
		@Override
		public int compareTo(Node o) {
			int c = Integer.compare(requirements.size(), o.requirements.size());
			if (c != 0)
				return c;
			
			return Character.compare(name, o.name);
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append("[ ");
			requirements.forEach(n -> { sb.append(n.name); sb.append(" "); });
			sb.append("] --> ");
			sb.append(name);
			sb.append(" --> [ ");
			following.forEach(n -> { sb.append(n.name); sb.append(" "); });
			sb.append("]");
			
			return sb.toString();
		}
	}
}
