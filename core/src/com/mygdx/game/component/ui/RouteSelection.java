package com.mygdx.game.component.ui;

import com.artemis.Component;
import com.artemis.Entity;

public class RouteSelection extends Component {
	public Integer from;
	public Integer to;
	public Entity route;
	
	public RouteSelection(Entity selected) {
		route = selected;
	}
}
