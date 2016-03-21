package com.mygdx.game.util;

import java.util.HashMap;

public enum MapTile {
	VERTICAL(4),
	HORIZONTAL(5),
	TURN_BOT_RIGHT(20),
	TURN_BOT_LEFT(21),
	TURN_TOP_LEFT(37),
	TURN_TOP_RIGHT(36),
	CROSSROAD_LEFT(17),
	CROSSROAD_RIGHT(19),
	CROSSROAD_TOP(2),
	CROSSROAD_BOTTOM(34),
	CROSSROAD_MIDDLE(18),
	CROSSROAD_MIDDLE_TOP(49),
	CROSSROAD_MIDDLE_RIGHT(50),
	CROSSROAD_MIDDLE_BOT(51),
	CROSSROAD_MIDDLE_LEFT(52),
	EMPTY(53),
	PARK_BOT(23),
	PARK_RIGHT(26),
	PARK_LEFT(27),
	PARK_TOP(39);
	
	private int id;
	private static HashMap<Integer, MapTile> idMap = new HashMap<>();
	
	static {
		for(MapTile tile : values()) {
			idMap.put(tile.id, tile);
		}
	}
	
	public static MapTile getTileForId(int id) {
		return idMap.get(id);
	}

	private MapTile(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
