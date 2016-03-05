package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Route;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class DriverTargetDrawSystem extends EntityProcessingSystem {
	
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Route> routeMapper;
	private CameraSystem cameraSystem;
	private ShapeRenderer shapeRenderer;

	public DriverTargetDrawSystem() {
		super(Aspect.all(Position.class,Driver.class,Route.class));
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	protected void process(Entity e) {
		Driver driver = driverMapper.get(e);
		Route route = routeMapper.get(e);
		
		if(driver.target != null) {
			shapeRenderer.setProjectionMatrix(cameraSystem.camera.combined);
			shapeRenderer.begin(ShapeType.Filled);
			
			shapeRenderer.setColor(Color.GREEN);
			Position position1 = positionMapper.get(route.from);
			shapeRenderer.circle(position1.x, position1.y, 4);
			
			shapeRenderer.setColor(Color.BLUE);
			Position position2 = positionMapper.get(route.to);
			shapeRenderer.circle(position2.x, position2.y, 4);
			
			if(driver.target != null) {
				shapeRenderer.setColor(Color.RED);
				Position position3 = positionMapper.get(driver.target);
				shapeRenderer.circle(position3.x, position3.y, 4);
			}
			shapeRenderer.end();
			shapeRenderer.setColor(Color.WHITE);
		}
	}
}
