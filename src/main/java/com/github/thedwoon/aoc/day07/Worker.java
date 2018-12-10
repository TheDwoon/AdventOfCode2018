package com.github.thedwoon.aoc.day07;

import com.github.thedwoon.aoc.Day07;

public class Worker {
	public Node currentNode;
	private int time;
	
	public Worker() {
		
	}
	
	public void finish(StringBuilder sb) {
		currentNode.following.forEach(f -> f.requirements.remove(currentNode));
		currentNode = null;			
	}
	
	public void setWorkload(Node node) {
		this.currentNode = node;
		this.time = Day07.getTimeForStep(node.name);
	}
	
	public boolean isBusy() {
		return time > 0;
	}
	
	public void passTime(int dt) {				
		this.time = Math.max(0, this.time - dt);			
	}
	
	public int getTimeRemaining() {
		return time;
	}
}