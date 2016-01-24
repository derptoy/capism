package com.mygdx.game.util;

import java.util.LinkedList;
import java.util.List;

public class MapPoint {
	private int id;
	private int x;
	private int y;
	
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
	
	
}
