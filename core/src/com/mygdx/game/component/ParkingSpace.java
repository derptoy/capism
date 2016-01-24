package com.mygdx.game.component;

import java.util.LinkedList;
import java.util.List;

import com.artemis.Component;
import com.artemis.Entity;

public class ParkingSpace extends Component {
	public int[][] positions;
	public List<Entity> parked = new LinkedList<>();
	
	public ParkingSpace(int[][] positions) {
		this.positions = positions;
	}
}
