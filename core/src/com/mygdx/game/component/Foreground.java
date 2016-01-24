package com.mygdx.game.component;

import com.artemis.Component;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Foreground extends Component {
	public TiledMapTileLayer layer;
	
	public Foreground(TiledMapTileLayer foreground) {
		this.layer = foreground;
	}
}
