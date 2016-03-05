package com.mygdx.game.component;

import java.util.LinkedList;

import com.artemis.Component;
import com.artemis.Entity;
import com.mygdx.game.component.Route.State;

public class Driver extends Component {
	public Entity target;
	public LinkedList<Entity> path;
	public float turnRate;
}
