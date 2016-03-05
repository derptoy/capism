package com.mygdx.game.util;

import java.util.LinkedList;
import java.util.List;

public class MapCells {
	private MapTile type;
	
	private MapPoint topInput;
	private MapPoint topOutput;
	
	private MapPoint botInput;
	private MapPoint botOutput;
	
	private MapPoint leftInput;
	private MapPoint leftOutput;
	
	private MapPoint rightInput;
	private MapPoint rightOutput;
	
	private List<MapPoint> innerPoints = new LinkedList<>();

	public MapCells(MapTile type) {
		this.type = type;
	}

	public MapTile getType() {
		return type;
	}

	public MapPoint getTopInput() {
		return topInput;
	}

	public void setTopInput(MapPoint topInput) {
		this.topInput = topInput;
	}

	public MapPoint getTopOutput() {
		return topOutput;
	}

	public void setTopOutput(MapPoint topOutput) {
		this.topOutput = topOutput;
	}

	public MapPoint getBotInput() {
		return botInput;
	}

	public void setBotInput(MapPoint botInput) {
		this.botInput = botInput;
	}

	public MapPoint getBotOutput() {
		return botOutput;
	}

	public void setBotOutput(MapPoint botOutput) {
		this.botOutput = botOutput;
	}

	public MapPoint getLeftInput() {
		return leftInput;
	}

	public void setLeftInput(MapPoint leftInput) {
		this.leftInput = leftInput;
	}

	public MapPoint getLeftOutput() {
		return leftOutput;
	}

	public void setLeftOutput(MapPoint leftOutput) {
		this.leftOutput = leftOutput;
	}

	public MapPoint getRightInput() {
		return rightInput;
	}

	public void setRightInput(MapPoint rightInput) {
		this.rightInput = rightInput;
	}

	public MapPoint getRightOutput() {
		return rightOutput;
	}

	public void setRightOutput(MapPoint rightOutput) {
		this.rightOutput = rightOutput;
	}

	public List<MapPoint> getInnerPoints() {
		return innerPoints;
	}

}
