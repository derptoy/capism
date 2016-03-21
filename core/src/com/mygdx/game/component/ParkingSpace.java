package com.mygdx.game.component;

import java.util.LinkedList;
import java.util.List;

import com.artemis.Component;
import com.artemis.Entity;
import com.mygdx.game.data.Offset;

public class ParkingSpace extends Component {
	public Offset[] positions;
	public boolean[] taken;
	public List<Entity> parked = new LinkedList<>();
	public Entity exit;
	
	public ParkingSpace(Offset[] positions, Entity exit) {
		this.positions = positions;
		this.exit = exit;
		taken = new boolean[positions.length];
	}
}
