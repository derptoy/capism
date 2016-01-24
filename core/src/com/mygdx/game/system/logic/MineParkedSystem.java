package com.mygdx.game.system.logic;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Cargo;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Mine;
import com.mygdx.game.component.Parking;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.data.Route.State;
import com.mygdx.game.system.passive.Astar;

@Wire
public class MineParkedSystem extends EntityProcessingSystem {
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Mine> mineMapper;
	
	private Astar astar;
	private ComponentMapper<Cargo> cargoMapper;
	private ComponentMapper<ParkingSpace> parkingSpaceMapper;
	
	public MineParkedSystem() {
		super(Aspect.all(Mine.class));
	}

	@Override
	protected void process(Entity e) {
		Mine mine = mineMapper.get(e);
		
		ParkingSpace parkingSpace = parkingSpaceMapper.get(mine.ParkingSpace);
		
		Iterator<Entity> iterator = parkingSpace.parked.iterator();
		while(iterator.hasNext()) {
			Entity ent = iterator.next();
			
			Driver driver = driverMapper.get(ent);
			Cargo cargo = cargoMapper.get(ent);
			
			if(cargo.lastLoad != 0 && cargo.load < cargo.max) {
				if( (System.currentTimeMillis() - cargo.lastLoad) > cargo.loadRate*1000 ) {
					if(mine != null && mine.outputs > 0) {
						cargo.load++;
						mine.outputs--;
					}
					
					cargo.lastLoad = System.currentTimeMillis();
				}
			} else if(mine!= null && cargo.load == cargo.max) {
				driver.path = astar.findPath(driver.route.from, driver.route.to);
				driver.target = driver.route.from;
				driver.route.state = State.TRAVEL_UNLOAD;
				cargo.lastLoad = 0;
				iterator.remove();
			} else if(cargo.lastLoad == 0) {
				driver.route.state = State.LOADING;
				cargo.lastLoad = System.currentTimeMillis();
			}
		}
	}
}