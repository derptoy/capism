package com.mygdx.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class Mine extends Component {
	public int outputs;
	public int tick;
	public int amount;
	public long lastAction;
	
	public Entity ParkingSpace;
	
	public Mine(int outputs, int amount, int tick, Entity parkingSpace) {
		this.outputs = outputs;
		this.amount = amount;
		this.tick = tick;
		ParkingSpace = parkingSpace;
	}
}
