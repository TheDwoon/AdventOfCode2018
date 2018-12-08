package com.github.thedwoon.aoc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Day08 extends AbstractDay {
	
	public static void main(String[] args) {
		new Day08().run();
	}

	@Override
	public void run() {
		List<Integer> input = getInput();
		
		Node root = parseNode(input.iterator());
		System.out.println("Stage 1: " + sumMetadata(root));
		System.out.println("Stage 2: " + sumMetadataSpecial(root));
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
	
	private List<Integer> getInput() {		
		List<Integer> input = new ArrayList<>();
		try (Scanner scanner = new Scanner(getResourceAsStream())) {
			while (scanner.hasNextInt()) {
				input.add(scanner.nextInt());
			}
		}
		
		return input;
	}

	private class Node {
		private List<Node> nodes = new ArrayList<>();
		private List<Integer> metadata = new ArrayList<>();
		
		private Node() {
			
		}
	}
}
