package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Mine;
import com.mygdx.game.component.Storage;

@Wire
public class MineSystem extends EntityProcessingSystem {
	private ComponentMapper<Mine> mineMapper;
	private ComponentMapper<Storage> storageMapper;
	
	public MineSystem() {
		super(Aspect.all(Mine.class, Storage.class));
	}

	@Override
	protected void process(Entity e) {
		Mine fac = mineMapper.get(e);
		Storage storage = storageMapper.get(e);
		long timeMillis = System.currentTimeMillis();
		if((timeMillis - fac.lastAction) > fac.tick*1000) {
			storage.output += fac.amount;
			fac.lastAction = timeMillis;
		}
	}
}