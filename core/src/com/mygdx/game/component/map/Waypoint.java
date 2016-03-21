package com.mygdx.game.component.map;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;
import com.artemis.Entity;

public class Waypoint extends Component {
	public boolean activator;
	public boolean exit;
	public List<Entity> neighbors = new ArrayList<>();
	public int id;
	
	public Waypoint(int id) {
		this.id = id;
	}

}
