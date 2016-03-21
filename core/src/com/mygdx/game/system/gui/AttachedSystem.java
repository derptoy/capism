package com.mygdx.game.system.gui;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Attached;
import com.mygdx.game.component.Position;

@Wire
public class AttachedSystem extends EntityProcessingSystem {
	
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Attached> atachedMapper;

	public AttachedSystem() {
		super(Aspect.all(Position.class,Attached.class));
	}

	@Override
	protected void process(Entity e) {
		Attached attached = atachedMapper.get(e);
		Position position = positionMapper.get(e);
		Position positionSource = positionMapper.get(attached.attachedTo);
		
		position.x = positionSource.x;
		position.y = positionSource.y;
	}


}
