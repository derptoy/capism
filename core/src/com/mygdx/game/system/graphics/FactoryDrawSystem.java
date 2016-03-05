package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.component.Factory;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Storage;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class FactoryDrawSystem extends EntityProcessingSystem {
	
	private CameraSystem cameraSystem;
	private AssetSystem assetSystem;
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Factory> factoryMapper;
	private ComponentMapper<Storage> storageMapper;
	
	private SpriteBatch batch;

	public FactoryDrawSystem() {
		super(Aspect.all(Position.class,Factory.class,Storage.class));
		batch = new SpriteBatch();
	}

	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		Factory fac = factoryMapper.get(e);
		Storage storage = storageMapper.get(e);
		
		BitmapFont font = assetSystem.getDefaultFont();
		
//		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
		font.setColor(Color.BLUE);
		font.draw(batch, ""+storage.input, position.x+8, position.y-15);
		font.setColor(Color.ORANGE);
		font.draw(batch, ""+storage.output, position.x+8, position.y+15);
		batch.end();
	}

}
