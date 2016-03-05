package com.mygdx.game.util;

import java.util.LinkedList;
import java.util.List;

public class MapPoint {
	private int id;
	private int x;
	private int y;
	private boolean activator;
	private boolean exit;
	
	private List<Integer> nextList = new LinkedList<>();
	
	public MapPoint(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public List<Integer> getNextList() {
		return nextList;
	}

	public int getId() {
		return id;
	}

	public boolean isActivator() {
		return activator;
	}

	public void setActivator(boolean activator) {
		this.activator = activator;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}
	
	
}
