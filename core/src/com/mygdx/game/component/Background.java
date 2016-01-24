package com.mygdx.game.component;

import com.artemis.Component;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Background extends Component {
	public TiledMapTileLayer layer;
	
	public Background(TiledMapTileLayer background) {
		this.layer = background;
	}
}
