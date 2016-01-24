package com.mygdx.game.system.logic;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Cargo;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Factory;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.data.Route.State;
import com.mygdx.game.system.passive.Astar;

@Wire
public class FactorySytem extends EntityProcessingSystem {
	private ComponentMapper<Factory> factoryMapper;
	private ComponentMapper<ParkingSpace> parkingSpaceMapper;
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Cargo> cargoMapper;
	
	private Astar astar;
	
	public FactorySytem() {
		super(Aspect.all(Factory.class));
	}

	@Override
	protected void process(Entity e) {
		Factory fac = factoryMapper.get(e);
		ParkingSpace parkingSpace = parkingSpaceMapper.get(fac.ParkingSpace);
		
		Iterator<Entity> iterator = parkingSpace.parked.iterator();
		while(iterator.hasNext()) {
			Entity ent = iterator.next();
			
			Driver driver = driverMapper.get(ent);
			driver.route.state = State.UNLOADING;
			Cargo cargo = cargoMapper.get(ent);
			if(driver != null && cargo.lastLoad != 0 && cargo.load > 0) {
				if( (System.currentTimeMillis() - cargo.lastLoad) > cargo.loadRate*1000 ) {
					fac.inputs++;
					cargo.load--;
					cargo.lastLoad = System.currentTimeMillis();
					
					if(cargo.load == 0) {
						driver.route.state = State.TRAVEL_LOAD;
						driver.path = astar.findPath(driver.route.to, driver.route.from);
						driver.target = driver.route.to;
						iterator.remove();
					}
				}
			}
			if(cargo.lastLoad == 0)
				cargo.lastLoad = System.currentTimeMillis();
		}
		
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