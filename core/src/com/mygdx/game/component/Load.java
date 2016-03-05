package com.mygdx.game.component;

import com.artemis.Component;
import com.artemis.Entity;

public class Load extends Component {
	public Entity loadFrom;
	
	public Load(Entity from) {
		loadFrom = from;
	}
}
