package com.mygdx.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Parking;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.component.Position;

@Wire
public class ParkingSystem extends EntityProcessingSystem {

	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Dir> directionMapper;
	private ComponentMapper<Position> posMapper;
	private ComponentMapper<Parking> parkingMapper;
	private ComponentMapper<ParkingSpace> parkingSpaceMapper;
	
	public ParkingSystem() {
		super(Aspect.all(Driver.class, Parking.class, Position.class));
	}
	
	@Override
	protected void process(Entity e) {
		Driver driver = driverMapper.get(e);
		Position pos = posMapper.get(e);
		Dir direction = directionMapper.get(e);
		Parking parking = parkingMapper.get(e);
		ParkingSpace parkingSpace = parkingSpaceMapper.get(parking.target);
		Position pos2 = posMapper.get(parking.target);
		
		if(parkingSpace != null) {
			parkingSpace.parked.add(e);
			e.edit().remove(Parking.class);
			direction.direction.set(-1, 0);
			pos.x = pos2.x+36;
			pos.y = pos2.y+19;
		}
	}

}
