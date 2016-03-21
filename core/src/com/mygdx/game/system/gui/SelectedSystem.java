package com.mygdx.game.system.gui;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.component.Bounds;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.map.Selected;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class SelectedSystem extends EntityProcessingSystem {

	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Bounds> boundsMapper;
	
	private CameraSystem cameraSystem;
	private ShapeRenderer shapeRenderer;
	
	public SelectedSystem() {
		super(Aspect.all(Position.class,Selected.class, Bounds.class));
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		Bounds bounds = boundsMapper.get(e);
		
		shapeRenderer.setProjectionMatrix(cameraSystem.camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(position.x - bounds.width/2, position.y - bounds.height/2, bounds.width, bounds.height);
		shapeRenderer.end();
		shapeRenderer.setColor(Color.WHITE);
	}

}
