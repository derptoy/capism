package com.mygdx.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class Attached extends Component {
	public Entity attachedTo;
	
	public Attached(Entity ent) {
		attachedTo = ent;
	}
}
