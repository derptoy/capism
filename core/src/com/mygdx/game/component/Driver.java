package com.mygdx.game.component;

import java.util.LinkedList;

import com.artemis.Component;
import com.artemis.Entity;

public class Driver extends Component {
	public Entity target;
	public LinkedList<Entity> path;
	public float turnRate;
	
	public long loadRate;
	public int load;
	public int max;
	public long lastLoad;
}
