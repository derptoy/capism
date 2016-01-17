package com.mygdx.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class Parked extends Component {

	public Entity target;

	public Parked(Entity target) {
		this.target = target;
	}

}
