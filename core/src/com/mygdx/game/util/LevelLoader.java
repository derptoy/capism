package com.mygdx.game.util;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.TexReg;
import com.mygdx.game.component.map.RoadTile;
import com.mygdx.game.component.map.Waypoint;
import com.mygdx.game.main.Game;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LevelLoader {
	
	private TmxMapLoader loader;
	private TiledMapTileLayer foreground;
	private TiledMapTileLayer background;
	private Entity[][] RoadTile;
	private int id;

	public LevelLoader() {
		loader = new TmxMapLoader();
	}
	
	public void load(String url) {
		TiledMap map = loader.load(url);
		
		foreground = (TiledMapTileLayer)(map.getLayers().get("foreground"));
		background = (TiledMapTileLayer)(map.getLayers().get("background"));
		
		// Udelme matici s bodama pro jednotlive policka
		id = 0;
		RoadTile = new Entity[background.getWidth()][background.getHeight()];
		for (int i = 0; i < background.getWidth(); i++) {
			for (int j = 0; j < background.getHeight(); j++) {
				Cell cell = background.getCell(i, j);
				if(cell != null)
					processCell(cell, i, j);
			}
		}
		
		// Spojime body mezi policky a vytvotime sit
	}

	private void processCell(Cell cell, int i, int j) {
		MapTile tile = MapTile.getTileForId(cell.getTile().getId());
		if(tile != null)
			RoadTile[i][j] = TileBuilder.buildTile(tile, i, j);
	}

	public TiledMapTileLayer getForeground() {
		return foreground;
	}

	public TiledMapTileLayer getBackground() {
		return background;
	}

	public Entity[][] getRoadTile() {
		return RoadTile;
	}

	
}
