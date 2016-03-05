package com.mygdx.game.system.graphics;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class MouseDrawSystem extends BaseSystem {
	
	private CameraSystem cameraSystem;
	
	private Vector3 aimAtTmp = new Vector3();
	private Vector3 unproject;

	private SpriteBatch batch;
	
	public MouseDrawSystem() {
		batch = new SpriteBatch();
	}

	@Override
	protected void processSystem() {
		aimAtTmp.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        unproject = cameraSystem.camera.unproject(aimAtTmp);
        
        batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
		
		batch.end();
	}

}
