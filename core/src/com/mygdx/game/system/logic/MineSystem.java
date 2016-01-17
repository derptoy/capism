package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Mine;

@Wire
public class MineSystem extends EntityProcessingSystem {
	private ComponentMapper<Mine> mineMapper;
	
	public MineSystem() {
		super(Aspect.all(Mine.class));
	}

	@Override
	protected void process(Entity e) {
		Mine fac = mineMapper.get(e);
		long timeMillis = System.currentTimeMillis();
		if((timeMillis - fac.lastAction) > fac.tick*1000) {
			fac.outputs += fac.amount;
			fac.lastAction = timeMillis;
		}
	}
}