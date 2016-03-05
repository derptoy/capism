package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Factory;
import com.mygdx.game.component.Storage;

@Wire
public class FactorySytem extends EntityProcessingSystem {
	private ComponentMapper<Factory> factoryMapper;
	private ComponentMapper<Storage> storageMapper;
	
	public FactorySytem() {
		super(Aspect.all(Factory.class,Storage.class));
	}

	@Override
	protected void process(Entity e) {
		Factory fac = factoryMapper.get(e);
		Storage storage = storageMapper.get(e);
		
		long timeMillis = System.currentTimeMillis();
		if((timeMillis - fac.lastAction) > fac.tick*1000) {
			if(storage.input > 0) {
				if(storage.input < fac.amount) {
					storage.output += storage.input;
					storage.input = 0;
				} else {
					storage.output += fac.amount;
					storage.input -= fac.amount;
				}
			}
			fac.lastAction = timeMillis;
		}
	}
}