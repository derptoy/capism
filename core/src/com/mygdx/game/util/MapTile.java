package com.mygdx.game.util;

public enum MapTile {
	VERTICAL(4),
	HORIZONTAL(5),
	TURN_BOT_RIGHT(9),
	TURN_BOT_LEFT(10),
	TURN_TOP_LEFT(15),
	TURN_TOP_RIGHT(14),
	CROSSROAD_LEFT(6),
	CROSSROAD_RIGHT(8),
	CROSSROAD_TOP(2),
	CROSSROAD_BOTTOM(12),
	CROSSROAD_MIDDLE(7),
	CROSSROAD_MIDDLE_TOP(16),
	CROSSROAD_MIDDLE_RIGHT(17),
	CROSSROAD_MIDDLE_BOT(18),
	CROSSROAD_MIDDLE_LEFT(19);
	
	private int id;

	private MapTile(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
