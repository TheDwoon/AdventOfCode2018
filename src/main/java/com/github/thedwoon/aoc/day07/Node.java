package com.github.thedwoon.aoc.day07;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
	public final char name;
	public List<Node> requirements = new ArrayList<>();
	public List<Node> following = new ArrayList<>();
	
	public Node(char name) {
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