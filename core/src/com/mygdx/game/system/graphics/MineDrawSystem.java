package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.component.Mine;
import com.mygdx.game.component.Position;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class MineDrawSystem extends EntityProcessingSystem {
	private CameraSystem cameraSystem;
	private AssetSystem assetSystem;
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Mine> mineMapper;
	
	private SpriteBatch batch;

	public MineDrawSystem() {
		super(Aspect.all(Position.class,Mine.class));
		batch = new SpriteBatch();
	}

	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		Mine fac = mineMapper.get(e);
		
		BitmapFont font = assetSystem.getDefaultFont();
		
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
		font.setColor(Color.ORANGE);
		font.draw(batch, ""+fac.outputs, position.x+8, position.y+15);
		batch.end();
	}
}
