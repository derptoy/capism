package com.mygdx.game.component;

import com.artemis.Component;

public class Cargo extends Component {
	public long loadRate;
	public int load;
	public int max;
	public long lastLoad;
	
	public Cargo(int loadRate, int max, int load) {
		this.loadRate = loadRate;
		this.max = max;
		this.load = load;
	}
}
