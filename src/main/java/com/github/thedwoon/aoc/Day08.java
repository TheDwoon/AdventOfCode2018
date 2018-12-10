package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.github.thedwoon.aoc.day08.Node;

public final class Day08 extends AbstractDay<Node> {
	
	public Day08() {
		super();
	}
	
	public static void main(String[] args) {
		new Day08().run();
	}

	@Override
	protected Node getInput() {		
		List<Integer> input = new ArrayList<>();
		try (Scanner scanner = new Scanner(getResourceAsStream())) {
			while (scanner.hasNextInt()) {
				input.add(scanner.nextInt());
			}
		}
		
		return parseNode(input.iterator());
	}

	@Override
	protected String runPart1(Node root) {
		return Integer.toString(sumMetadata(root));
	}
	
	@Override
	protected String runPart2(Node root) {
		return Integer.toString(sumMetadataSpecial(root));
	}
	
	private int sumMetadata(Node node) {
		int sum = 0;
		for (int i : node.metadata)
			sum += i;
		
		for (Node n : node.nodes)
			sum += sumMetadata(n);
		
		return sum;
	}
	
	private int sumMetadataSpecial(Node node) {
		int sum = 0;
		if (node.nodes.isEmpty()) {
			for (int i : node.metadata)
				sum += i;
		} else {
			for (int i : node.metadata) {
				if (i - 1 < node.nodes.size())
					sum += sumMetadataSpecial(node.nodes.get(i - 1));
			}
		}
		
		return sum;
	}
	
	private Node parseNode(Iterator<Integer> it) {
		Node node = new Node();
		int nodeCount = it.next();			
		int metaCount = it.next();
		
		for (int i = 0; i < nodeCount; i++) 
			node.nodes.add(parseNode(it));
		
		for (int i = 0; i < metaCount; i++) 
			node.metadata.add(it.next());
		
		return node;
	}
}
