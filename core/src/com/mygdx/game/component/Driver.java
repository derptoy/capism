package com.mygdx.game.component;

import java.util.LinkedList;

import com.artemis.Component;
import com.artemis.Entity;
import com.mygdx.game.data.Route;
import com.mygdx.game.data.Route.State;

public class Driver extends Component {
	public Entity target;
	public LinkedList<Entity> path;
	public float turnRate;
	public Route route = new Route(State.TRAVEL_LOAD);
}
