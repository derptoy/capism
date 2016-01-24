package com.mygdx.game.data;

import com.artemis.Entity;

public class Route {
	public enum State{TRAVEL_LOAD, LOADING, TRAVEL_UNLOAD, UNLOADING};
	
	public Entity from;
	public Entity to;
	public State state;
	
	public Route(State state) {
		this.state = state;
	}

}
