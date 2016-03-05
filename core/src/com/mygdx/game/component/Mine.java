package com.mygdx.game.component;

import com.artemis.Component;

public class Mine extends Component {
	public int tick;
	public int amount;
	public long lastAction;
	
	public Mine(int amount, int tick) {
		this.amount = amount;
		this.tick = tick;
	}
}
