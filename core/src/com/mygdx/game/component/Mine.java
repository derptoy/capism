package com.mygdx.game.component;

import com.artemis.Component;

public class Mine extends Component {
	public int outputs;
	public int tick;
	public int amount;
	public long lastAction;
	
	public Mine(int outputs, int amount, int tick) {
		this.outputs = outputs;
		this.amount = amount;
		this.tick = tick;
	}
}
