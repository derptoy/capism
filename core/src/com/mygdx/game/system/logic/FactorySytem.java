package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Factory;

@Wire
public class FactorySytem extends EntityProcessingSystem {
	private ComponentMapper<Factory> mineMapper;
	
	public FactorySytem() {
		super(Aspect.all(Factory.class));
	}

	@Override
	protected void process(Entity e) {
		Factory fac = mineMapper.get(e);
		long timeMillis = System.currentTimeMillis();
		if((timeMillis - fac.lastAction) > fac.tick*1000) {
			if(fac.inputs > 0) {
				if(fac.inputs < fac.amount) {
					fac.outputs += fac.inputs;
					fac.inputs = 0;
				} else {
					fac.outputs += fac.amount;
					fac.inputs -= fac.amount;
				}
			}
			fac.lastAction = timeMillis;
		}
	}
}