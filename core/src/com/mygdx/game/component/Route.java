package com.mygdx.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class Route extends Component {
	public enum State{TRAVEL_LOAD, LOADING, LOADED, TRAVEL_UNLOAD, UNLOADING, UNLOADED};
	
	public Entity from;
	public Entity to;
	public State state;
	
	public Route(State state) {
		this.state = state;
	}

}
