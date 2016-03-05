package com.mygdx.game.system.gui;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.ui.SnapToGrid;

@Wire
public class SnapToGridSystem extends EntityProcessingSystem {
	
	private ComponentMapper<Position> positionMapper;

	public SnapToGridSystem() {
		super(Aspect.all(Position.class,SnapToGrid.class));
	}

	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		
		int gridX = (int) ((position.x)/32);
		int gridY = (int) ((position.y)/32);
		
		position.x = gridX*32 + 16;
		position.y = gridY*32 + 16;
	}

}
