package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Load;
import com.mygdx.game.component.Parking;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Route;
import com.mygdx.game.component.Unload;

@Wire
public class ParkingSystem extends EntityProcessingSystem {

//	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Route> routeMapper;
	private ComponentMapper<Dir> directionMapper;
	private ComponentMapper<Position> posMapper;
	private ComponentMapper<Parking> parkingMapper;
	private ComponentMapper<ParkingSpace> parkingSpaceMapper;
	
	public ParkingSystem() {
		super(Aspect.all(Driver.class, Parking.class, Position.class, Route.class));
	}
	
	@Override
	protected void process(Entity e) {
		Position pos = posMapper.get(e);
		Dir direction = directionMapper.get(e);
		Parking parking = parkingMapper.get(e);
		Route route = routeMapper.get(e);

		// Load
		if(parking.target.id == route.from.id) {
			e.edit().add(new Load(route.from));
		} else {
			e.edit().add(new Unload(route.to));
		}
		
		ParkingSpace parkingSpace = parkingSpaceMapper.get(parking.target);
		if(parkingSpace != null) {
			for(int i=0;i<parkingSpace.taken.length;i++) {
				if(!parkingSpace.taken[i]) {
					//				parkingSpace.taken[i] = true;
					direction.direction.set(-1, 0);
					pos.x += parkingSpace.positions[i].getX();
					pos.y += parkingSpace.positions[i].getY();

					break;
				}
			}
		}
			
		e.edit().remove(Parking.class);
	}

}
