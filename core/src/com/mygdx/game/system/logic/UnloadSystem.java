package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Cargo;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Parking;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.component.Route;
import com.mygdx.game.component.Route.State;
import com.mygdx.game.component.Storage;
import com.mygdx.game.component.Unload;
import com.mygdx.game.system.passive.Astar;
import com.sun.media.sound.UlawCodec;

@Wire
public class UnloadSystem extends EntityProcessingSystem {
//	private ComponentMapper<Factory> factoryMapper;
//	private ComponentMapper<Unload> unloadMapper;
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Cargo> cargoMapper;
	private ComponentMapper<Storage> storageMapper;
	private ComponentMapper<Unload> unloadMapper;
	private ComponentMapper<Route> routeMapper;
	private ComponentMapper<ParkingSpace> parkingSpaceMapper;
	
	private Astar astar;
	
	public UnloadSystem() {
		super(Aspect.all(Driver.class, Unload.class,Cargo.class,Route.class));
	}

	@Override
	protected void process(Entity e) {
		Driver driver = driverMapper.get(e);
		Route route = routeMapper.get(e);
		route.state = State.UNLOADING;
		Cargo cargo = cargoMapper.get(e);
		Unload unload = unloadMapper.get(e);
		Storage storage = storageMapper.get(unload.unloadTo);
		ParkingSpace parkingSpace = parkingSpaceMapper.get(unload.unloadTo);
		
		if(driver != null && cargo.lastLoad != 0 && cargo.load > 0) {
			if( (System.currentTimeMillis() - cargo.lastLoad) > cargo.loadRate*1000 ) {
				storage.input++;
				cargo.load--;
				cargo.lastLoad = System.currentTimeMillis();

				if(cargo.load == 0) {
					route.state = State.TRAVEL_LOAD;
					driver.path = astar.findPath(parkingSpace.exit, route.from);
					driver.target = parkingSpace.exit;
//					e.edit().remove(Parking.class);
					e.edit().remove(Unload.class);
				}
			}
		} else if(cargo.lastLoad == 0)
			cargo.lastLoad = System.currentTimeMillis();
	}

}
