package com.mygdx.game.component;

import com.artemis.Component;

public class Factory extends Component {
	public int tick;
	public int amount;
	public long lastAction;
	
	public Factory(int amount, int tick) {
		this.amount = amount;
		this.tick = tick;
	}
}
