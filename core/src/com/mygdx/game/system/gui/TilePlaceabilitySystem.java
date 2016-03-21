package com.mygdx.game.system.gui;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.TexReg;
import com.mygdx.game.component.map.Map;
import com.mygdx.game.component.ui.PlacebleTile;
import com.mygdx.game.main.Game;

@Wire
public class TilePlaceabilitySystem extends EntityProcessingSystem {
	
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<PlacebleTile> placebleTileMapper;
	private ComponentMapper<TexReg> texRegMapper;
	private ComponentMapper<Map> mapMapper;

	public TilePlaceabilitySystem() {
		super(Aspect.all(Position.class,PlacebleTile.class,TexReg.class));
	}

	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		PlacebleTile placebleTile = placebleTileMapper.get(e);
		TexReg texReg = texRegMapper.get(e);
		Entity mapEntity = Game.world.getManager(TagManager.class).getEntity("map");
		Map map = mapMapper.get(mapEntity);
		
		int i = (int) (position.x/32);
		int j = (int) (position.y/32);
		

		if(i >= 0 && i < map.roadTiles.length
				&& j >=0 && j < map.roadTiles[0].length) {
			
			if(map.roadTiles[i][j] == null) {
				placebleTile.placeble = true;
				texReg.color.set(0.7f, 1f, 0.7f, 0.7f);
			} else {
				placebleTile.placeble = false;
				texReg.color.set(1f, 0.7f, 0.7f, 0.7f);
			}
		} else {
			placebleTile.placeble = false;
			texReg.color.set(1f, 0.7f, 0.7f, 0.7f);
		}
		
	}
}
