package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Cargo;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Load;
import com.mygdx.game.component.Parking;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.component.Route;
import com.mygdx.game.component.Route.State;
import com.mygdx.game.component.Storage;
import com.mygdx.game.system.passive.Astar;

@Wire
public class LoadSystem extends EntityProcessingSystem {
	private ComponentMapper<Load> loadMapper;
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Cargo> cargoMapper;
	private ComponentMapper<Storage> storageMapper;
	private ComponentMapper<Route> routeMapper;
	private ComponentMapper<ParkingSpace> parkingSpaceMapper;
	
	private Astar astar;
	
	public LoadSystem() {
		super(Aspect.all(Driver.class, Load.class,Cargo.class, Route.class));
	}

	@Override
	protected void process(Entity e) {
		Driver driver = driverMapper.get(e);
		Cargo cargo = cargoMapper.get(e);
		Load load = loadMapper.get(e);
		Route route = routeMapper.get(e);
		Storage storage = storageMapper.get(load.loadFrom);
		ParkingSpace parkingSpace = parkingSpaceMapper.get(load.loadFrom);
		
		if(cargo.lastLoad != 0 && cargo.load < cargo.max) {
			if( (System.currentTimeMillis() - cargo.lastLoad) > cargo.loadRate*1000 ) {
				if(storage != null && storage.output > 0) {
					cargo.load++;
					storage.output--;
				}
				
				cargo.lastLoad = System.currentTimeMillis();
			}
		} else if(cargo.load == cargo.max) {
			route.state = State.TRAVEL_UNLOAD;
			driver.path = astar.findPath(parkingSpace.exit, route.to);
			driver.target = parkingSpace.exit;
			cargo.lastLoad = 0;
//			e.edit().remove(Parking.class);
			e.edit().remove(Load.class);
		} else if(cargo.lastLoad == 0) {
			route.state = State.LOADING;
			cargo.lastLoad = System.currentTimeMillis();
		}
	}

}
