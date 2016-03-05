package com.mygdx.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class Unload extends Component {
	public Entity unloadTo;
	
	public Unload(Entity to) {
		unloadTo = to;
	}
}
