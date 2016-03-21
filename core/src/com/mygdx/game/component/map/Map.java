package com.mygdx.game.component.map;

import com.artemis.Component;
import com.artemis.Entity;

public class Map extends Component {
	public Entity[][] roadTiles;
	
	public Map(Entity[][] roadTiles2) {
		roadTiles = roadTiles2;
	}
}
