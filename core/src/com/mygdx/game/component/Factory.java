package com.mygdx.game.component;

import com.artemis.Component;

public class Factory extends Component {
	public int inputs;
	public int outputs;
	public int tick;
	public int amount;
	public long lastAction;
	
	public Factory(int inputs, int outputs, int amount, int tick) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.amount = amount;
		this.tick = tick;
	}
}
