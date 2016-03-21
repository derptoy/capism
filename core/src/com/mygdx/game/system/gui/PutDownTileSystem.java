package com.mygdx.game.system.gui;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.map.TileReplaceAction;
import com.mygdx.game.component.ui.PlacebleTile;
import com.mygdx.game.main.Game;
import com.mygdx.game.system.passive.CameraSystem;
import com.mygdx.game.util.MapTile;
import com.mygdx.game.util.TileBuilder;

@Wire
public class PutDownTileSystem extends EntityProcessingSystem {
	
	private CameraSystem cameraSystem;
	private Vector3 aimAtTmp = new Vector3();
	private Vector3 unproject;

	public PutDownTileSystem() {
		super(Aspect.all(Position.class,PlacebleTile.class));
	}

	@Override
	protected void process(Entity e) {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			Entity map = Game.world.getManager(TagManager.class).getEntity("map");
			TileReplaceAction tileReplaceAction = new TileReplaceAction();
			aimAtTmp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	        unproject = cameraSystem.camera.unproject(aimAtTmp);
	        int i = (int) (unproject.x/32);
	        int j = (int) (unproject.y/32);
	        
			Entity ent = TileBuilder.buildTile(MapTile.PARK_BOT, i, j);
			
			tileReplaceAction.i = i;
			tileReplaceAction.j = j;
			tileReplaceAction.tile = ent;
			
			map.edit().add(tileReplaceAction);
			
			e.deleteFromWorld();
		}
	}
	
}
