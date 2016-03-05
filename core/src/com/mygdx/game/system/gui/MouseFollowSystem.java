package com.mygdx.game.system.gui;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.ui.MouseFollow;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class MouseFollowSystem extends EntityProcessingSystem {
	
	private CameraSystem cameraSystem;
	
	private Vector3 aimAtTmp = new Vector3();
	private Vector3 unproject;
	
	private ComponentMapper<Position> positionMapper;

	public MouseFollowSystem() {
		super(Aspect.all(Position.class,MouseFollow.class));
	}

	@Override
	protected void begin() {
		aimAtTmp.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        unproject = cameraSystem.camera.unproject(aimAtTmp);
	}

	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		
		position.x = unproject.x;
		position.y = unproject.y;
	}


}
