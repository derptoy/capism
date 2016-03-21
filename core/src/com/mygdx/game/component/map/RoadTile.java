package com.mygdx.game.component.map;

import java.util.LinkedList;
import java.util.List;

import com.artemis.Component;
import com.artemis.Entity;
import com.mygdx.game.util.MapTile;

public class RoadTile extends Component {
	
	private MapTile type;
	
	private Entity topInput;
	private Entity topOutput;
	
	private Entity botInput;
	private Entity botOutput;
	
	private Entity leftInput;
	private Entity leftOutput;
	
	private Entity rightInput;
	private Entity rightOutput;
	
	private List<Entity> innerPoints = new LinkedList<>();
	
	private Entity activator;
	private Entity exit;

	public RoadTile(MapTile maptile) {
		type = maptile;
	}

	public MapTile getType() {
		return type;
	}

	public Entity getTopInput() {
		return topInput;
	}

	public void setTopInput(Entity topInput) {
		this.topInput = topInput;
	}

	public Entity getTopOutput() {
		return topOutput;
	}

	public void setTopOutput(Entity topOutput) {
		this.topOutput = topOutput;
	}

	public Entity getBotInput() {
		return botInput;
	}

	public void setBotInput(Entity botInput) {
		this.botInput = botInput;
	}

	public Entity getBotOutput() {
		return botOutput;
	}

	public void setBotOutput(Entity botOutput) {
		this.botOutput = botOutput;
	}

	public Entity getLeftInput() {
		return leftInput;
	}

	public void setLeftInput(Entity leftInput) {
		this.leftInput = leftInput;
	}

	public Entity getLeftOutput() {
		return leftOutput;
	}

	public void setLeftOutput(Entity leftOutput) {
		this.leftOutput = leftOutput;
	}

	public Entity getRightInput() {
		return rightInput;
	}

	public void setRightInput(Entity rightInput) {
		this.rightInput = rightInput;
	}

	public Entity getRightOutput() {
		return rightOutput;
	}

	public void setRightOutput(Entity rightOutput) {
		this.rightOutput = rightOutput;
	}

	public List<Entity> getInnerPoints() {
		return innerPoints;
	}

	public void setInnerPoints(List<Entity> innerPoints) {
		this.innerPoints = innerPoints;
	}

	public Entity getActivator() {
		return activator;
	}

	public void setActivator(Entity activator) {
		this.activator = activator;
	}

	public Entity getExit() {
		return exit;
	}

	public void setExit(Entity exit) {
		this.exit = exit;
	}

}
