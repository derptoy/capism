package com.mygdx.game.system.map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.map.Map;
import com.mygdx.game.component.map.RoadTile;
import com.mygdx.game.component.map.TileReplaceAction;
import com.mygdx.game.component.map.Waypoint;
import com.mygdx.game.main.Game;

@Wire
public class MapReplaceTileSystem extends EntityProcessingSystem {
	
	private ComponentMapper<Map> mapMapper;
	private ComponentMapper<TileReplaceAction> tileReplaceActionMapper;
	private ComponentMapper<RoadTile> roadTileMapper;
	private ComponentMapper<Waypoint> waypointMapper;

	public MapReplaceTileSystem() {
		super(Aspect.all(Map.class,TileReplaceAction.class));
	}

	@Override
	protected void process(Entity e) {
		Map map = mapMapper.get(e);
		TileReplaceAction tileReplaceAction = tileReplaceActionMapper.get(e);
		
		int i = tileReplaceAction.i;
		int j = tileReplaceAction.j;
		Entity toReplace = map.roadTiles[i][j];
		Entity with = tileReplaceAction.tile;
		RoadTile roadTileNew = roadTileMapper.get(with);

		if(toReplace != null) {
			RoadTile roadTileOld = roadTileMapper.get(toReplace);
			// Transfer output waypoints
			if(roadTileOld.getBotOutput() != null && roadTileNew.getBotOutput() != null)
				waypointMapper.get(roadTileNew.getBotOutput()).neighbors.addAll(waypointMapper.get(roadTileOld.getBotOutput()).neighbors);
			if(roadTileOld.getLeftOutput() != null && roadTileNew.getLeftOutput() != null)
				waypointMapper.get(roadTileNew.getLeftOutput()).neighbors.addAll(waypointMapper.get(roadTileOld.getLeftOutput()).neighbors);
			if(roadTileOld.getTopOutput() != null && roadTileNew.getTopOutput() != null)
				waypointMapper.get(roadTileNew.getTopOutput()).neighbors.addAll(waypointMapper.get(roadTileOld.getTopOutput()).neighbors);
			if(roadTileOld.getRightOutput() != null && roadTileNew.getRightOutput() != null)
				waypointMapper.get(roadTileNew.getRightOutput()).neighbors.addAll(waypointMapper.get(roadTileOld.getRightOutput()).neighbors);
			
			if(roadTileOld.getBotOutput() != null)
				Game.world.deleteEntity(roadTileOld.getBotOutput());
			if(roadTileOld.getLeftOutput() != null)
				Game.world.deleteEntity(roadTileOld.getLeftOutput());
			if(roadTileOld.getTopOutput() != null)
				Game.world.deleteEntity(roadTileOld.getTopOutput());
			if(roadTileOld.getRightOutput() != null)
				Game.world.deleteEntity(roadTileOld.getRightOutput());
			
			for(Entity ent : roadTileOld.getInnerPoints()) {
				Game.world.deleteEntity(ent);
			}
			
			Game.world.deleteEntity(toReplace);
		}
		
		map.roadTiles[i][j] = with;
		
		// Replace inputs with other tile outputs
		if(map.roadTiles[i][j] != null) {
			RoadTile tileReplacing = roadTileMapper.get(map.roadTiles[i][j]);
			if(i-1 < map.roadTiles.length && map.roadTiles[i-1][j] != null) {
				RoadTile leftTile =  roadTileMapper.get(map.roadTiles[i-1][j]);
				// Na nasledujicim Tile odstranime left body a nahradime je right body s aktalniho Tile
				if(leftTile.getRightOutput() != null && tileReplacing.getLeftInput() != null ) {
					waypointMapper.get(leftTile.getRightOutput()).neighbors = waypointMapper.get(tileReplacing.getLeftInput()).neighbors;
					Game.world.deleteEntity(tileReplacing.getLeftInput());
					tileReplacing.setLeftInput(null);
				} else if(leftTile.getRightOutput() != null) {
					waypointMapper.get(leftTile.getRightOutput()).neighbors.clear();
				}
			}
			if(i+1 < map.roadTiles.length && map.roadTiles[i+1][j] != null) {
				RoadTile rightTile =  roadTileMapper.get(map.roadTiles[i+1][j]);
				// Na nasledujicim Tile odstranime left body a nahradime je right body s aktalniho Tile
				if(rightTile.getLeftOutput() != null && tileReplacing.getRightInput() != null ) {
					waypointMapper.get(rightTile.getLeftOutput()).neighbors = waypointMapper.get(tileReplacing.getRightInput()).neighbors;
					Game.world.deleteEntity(tileReplacing.getRightInput());
					tileReplacing.setRightInput(null);
				} else if(rightTile.getLeftOutput() != null) {
					waypointMapper.get(rightTile.getLeftOutput()).neighbors.clear();
				}
			}

			if(j-1 < map.roadTiles[i].length && map.roadTiles[i][j-1] != null) {
				RoadTile bottomTile = roadTileMapper.get(map.roadTiles[i][j-1]);
				if(bottomTile.getTopOutput() != null && tileReplacing.getBotInput() != null ) {
					waypointMapper.get(bottomTile.getTopOutput()).neighbors = waypointMapper.get(tileReplacing.getBotInput()).neighbors;
					Game.world.deleteEntity(tileReplacing.getBotInput());
					tileReplacing.setBotInput(null);
				} else if(bottomTile.getTopOutput() != null) {
					waypointMapper.get(bottomTile.getTopOutput()).neighbors.clear();
				}
			}
			
			if(j+1 < map.roadTiles[i].length && map.roadTiles[i][j+1] != null) {
				RoadTile topTile = roadTileMapper.get(map.roadTiles[i][j+1]);
				if(topTile.getBotOutput() != null && tileReplacing.getTopInput() != null ) {
					waypointMapper.get(topTile.getBotOutput()).neighbors = waypointMapper.get(tileReplacing.getTopInput()).neighbors;
					Game.world.deleteEntity(tileReplacing.getTopInput());
					tileReplacing.setTopInput(null);
				} else if(topTile.getBotOutput() != null) {
					waypointMapper.get(topTile.getBotOutput()).neighbors.clear();
				}
			}
		}
		
		e.edit().remove(TileReplaceAction.class);
	}
	
}
