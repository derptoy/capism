package com.mygdx.game.system.graphics;


import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Waypoint;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class WaypointSystem extends EntityProcessingSystem {

	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Waypoint> waypointMapper;
	private ShapeRenderer shapeRenderer;
	private CameraSystem cameraSystem;
	
	public WaypointSystem() {
		super(Aspect.all(Position.class, Waypoint.class));
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		Waypoint waypoint = waypointMapper.get(e);
		
		shapeRenderer.setProjectionMatrix(cameraSystem.camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.circle(position.x, position.y, 5);
		for(Entity ent : waypoint.neighbors) {
			Position pos = positionMapper.get(ent);
			
//			if(Math.abs(position.x-pos.x) > 50)
//				shapeRenderer.setColor(Color.RED);
//			else
//				shapeRenderer.setColor(Color.WHITE);
			
//			if(position.x > pos.x)
//				shapeRenderer.line(position.x, position.y+2, pos.x, pos.y+2);
//			else if(position.y > pos.y)
//				shapeRenderer.line(position.x+2, position.y, pos.x+2, pos.y);
//			else
//				shapeRenderer.line(position.x, position.y, pos.x, pos.y);
			
				shapeRenderer.setColor(Color.WHITE);
			
			shapeRenderer.line(position.x, position.y, pos.x, pos.y);
		}
		shapeRenderer.end();
		shapeRenderer.setColor(Color.WHITE);
	}

}
