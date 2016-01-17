package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Mine;
import com.mygdx.game.component.Parked;
import com.mygdx.game.system.passive.Astar;

@Wire
public class MineParkedSystem extends EntityProcessingSystem {
	private ComponentMapper<Parked> parkedMapper;
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Mine> mineMapper;
	private TagManager tagManager;
	
	private Astar astar;
	
	public MineParkedSystem() {
		super(Aspect.all(Driver.class, Parked.class));
	}

	@Override
	protected void process(Entity e) {
		Driver driver = driverMapper.get(e);
		Parked parked = parkedMapper.get(e);
		Mine mine = mineMapper.get(parked.target);
		
		if(driver.lastLoad != 0 && driver.load < driver.max) {
			if( (System.currentTimeMillis() - driver.lastLoad) > driver.loadRate*1000 ) {
				if(mine.outputs > 0) {
					driver.load++;
					mine.outputs--;
				}
				
				driver.lastLoad = System.currentTimeMillis();
			}
		} else if(driver.load == driver.max) {
			e.edit().remove(Parked.class);
			Entity target = tagManager.getEntity("factory");
//			tagManager.register("target", target);
			driver.path = astar.findPath(parked.target, target);
			driver.target = parked.target;
		}
			
		if(driver.lastLoad == 0)
			driver.lastLoad = System.currentTimeMillis();
	}
}