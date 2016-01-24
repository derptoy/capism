package com.mygdx.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class Parking extends Component {

	public Entity target;
	
	public Parking(Entity target2) {
		target = target2;
	}
}
