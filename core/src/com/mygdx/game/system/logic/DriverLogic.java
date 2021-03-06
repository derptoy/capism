package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Parking;
import com.mygdx.game.component.Position;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class DriverLogic extends EntityProcessingSystem {

	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Dir> dirMapper;
	
	private CameraSystem cameraSystem;
	
	private Vector2 target = new Vector2();
	private Vector2 driverVector = new Vector2();
	
	public DriverLogic() {
		super(Aspect.all(Position.class, Driver.class, Dir.class));
	}
	
	@Override
	protected void process(Entity e) {
		Driver driver = driverMapper.get(e);
		Position position = positionMapper.get(e);
		
		if(driver.target != null) {
			Position pos = positionMapper.get(driver.target);
			target.x = pos.x;
			target.y = pos.y;
			driverVector.x = position.x;
			driverVector.y = position.y;
			target.sub(driverVector);
			float distance = target.len();
			target.nor();
			
			if(distance > 5) {
				Dir dir = dirMapper.get(e);
				
				float angle = target.angle(dir.direction);
				if(angle > 5)
					dir.direction.rotate(-driver.turnRate * Gdx.graphics.getDeltaTime());
				else if(angle < -5 )
					dir.direction.rotate(driver.turnRate * Gdx.graphics.getDeltaTime());
				else
					dir.direction.set(target);
			
				position.x += dir.direction.x * dir.speed * Gdx.graphics.getDeltaTime();
				position.y += dir.direction.y * dir.speed * Gdx.graphics.getDeltaTime();
			} else {
				Entity poll = driver.path.poll();
				// we arrived at destination
				if(poll == null && driver.target != null) {
					e.edit().add(new Parking(driver.target));
				}
				driver.target = poll;
			}
		}
	}

}
