package com.mygdx.game.util;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.mygdx.game.component.Bounds;
import com.mygdx.game.component.Factory;
import com.mygdx.game.component.Mine;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Storage;
import com.mygdx.game.component.Tex;
import com.mygdx.game.component.TexReg;
import com.mygdx.game.component.map.RoadTile;
import com.mygdx.game.component.map.Selectable;
import com.mygdx.game.component.map.Waypoint;
import com.mygdx.game.data.Offset;
import com.mygdx.game.main.Game;

public class TileBuilder {
	
	public static int id = 0;
	private static int count;
	
	public static Entity buildTile(MapTile tile, int i, int j) {
		switch(tile) {
		case VERTICAL:
			return createVertical(i,j,MapTile.VERTICAL);
		case CROSSROAD_BOTTOM:
			return createVertical(i,j,MapTile.CROSSROAD_BOTTOM);
		case CROSSROAD_LEFT:
			return createHorizontal(i,j,MapTile.CROSSROAD_LEFT);
		case CROSSROAD_RIGHT:
			return createHorizontal(i,j, MapTile.CROSSROAD_RIGHT);
		case CROSSROAD_TOP:
			return createVertical(i,j,MapTile.CROSSROAD_TOP);
		case CROSSROAD_MIDDLE:
		case CROSSROAD_MIDDLE_BOT:
		case CROSSROAD_MIDDLE_LEFT:
		case CROSSROAD_MIDDLE_RIGHT:
		case CROSSROAD_MIDDLE_TOP:
			return createCrossroadMiddle(tile,i,j);
		case EMPTY:
			return null;
		case HORIZONTAL:
			return createHorizontal(i,j,MapTile.HORIZONTAL);
		case PARK_BOT:
			return createParkBot(i,j);
		case PARK_LEFT:
			return createParkLeft(i,j);
		case PARK_RIGHT:
			return createParkRight(i,j);
		case PARK_TOP:
			return createParkTop(i,j);
		case TURN_BOT_LEFT:
			return createTurnBotLeft(i,j);
		case TURN_BOT_RIGHT:
			return createTurnBotRight(i,j);
		case TURN_TOP_LEFT:
			return createTurnTopLeft(i,j);
		case TURN_TOP_RIGHT:
			return createTurnTopRight(i,j);
		default:
			return null;
		}
	}
	
	private static Entity createParkRight(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,16, 23,i,j);
		Entity point2 = createWaypoint(id++,16, 7,i,j);
		Entity point3 = createWaypoint(id++,31, 23,i,j);
		Entity point4 = createWaypoint(id++,31, 7,i,j);
		waypointMapper.get(point1).activator = true;
		waypointMapper.get(point2).exit = true;
		
		waypointMapper.get(point3).neighbors.add(point1);
		waypointMapper.get(point2).neighbors.add(point4);
		waypointMapper.get(point1).neighbors.add(point2);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.PARK_RIGHT);
		mc.getInnerPoints().add(point1);
		mc.getInnerPoints().add(point2);
		mc.setRightInput(point3);
		mc.setRightOutput(point4);
		mc.setActivator(point1);
		mc.setExit(point2);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.PARK_RIGHT.toString()));
		
		Offset[] positions = new Offset[2];
//		if(mapCells.getType() == MapTile.PARK_BOT)
//			positions[0] = new Offset(-32, 32);
//		else if(mapCells.getType() == MapTile.PARK_TOP)
//			positions[0] = new Offset(-32, -32);
//		else if(mapCells.getType() == MapTile.PARK_LEFT)
//			positions[0] = new Offset(32, 32);
//		else
			positions[0] = new Offset(-32, 32);
			point1.edit().add(new ParkingSpace(positions, point2));
		createFactoryMine(point1);
		return ent;
	}
	
	private static void createFactoryMine(Entity ent) {
		GroupManager groupManager = Game.world.getManager(GroupManager.class);
		if(count%2==0) {
			Mine min1 = new Mine(1, 2);
			Tex texMine = new Tex("mine",0,50);
//			Position poss = new Position(offsetX+i*32, offsetY+j*32);
			Storage storage = new Storage();
			ent.edit().add(min1).add(texMine).add(storage).add(new Selectable()).add(new Bounds(50, 50));
			groupManager.add(ent, "mine");
		} else {
			Factory fac2 = new Factory(1, 5);
//			Position poss = new Position(offsetX+i*32, offsetY+j*32);
			Storage storage = new Storage();
			Tex tex = new Tex("factory", 50, 0);
			ent.edit().add(fac2).add(tex).add(storage).add(new Selectable()).add(new Bounds(50, 50));
			groupManager.add(ent, "factory");
		}
		System.out.println("FM: "+ent);
		count++;
	}

	private static Entity createParkLeft(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,16, 23,i,j);
		Entity point2 = createWaypoint(id++,16, 7,i,j);
		Entity point3 = createWaypoint(id++,0, 23,i,j);
		Entity point4 = createWaypoint(id++,0, 7,i,j);
		waypointMapper.get(point2).activator = true;
		waypointMapper.get(point1).exit = true;
		
		waypointMapper.get(point1).neighbors.add(point3);
		waypointMapper.get(point4).neighbors.add(point2);
		waypointMapper.get(point2).neighbors.add(point1);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.PARK_LEFT);
		mc.getInnerPoints().add(point1);
		mc.getInnerPoints().add(point2);
		mc.setLeftInput(point4);
		mc.setLeftOutput(point3);
		mc.setActivator(point2);
		mc.setExit(point1);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.PARK_LEFT.toString()));
		
		Offset[] positions = new Offset[2];
		positions[0] = new Offset(32, 32);
		point2.edit().add(new ParkingSpace(positions, point2));
		createFactoryMine(point2);
		return ent;
	}

	private static Entity createParkTop(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,23, 16,i,j);
		Entity point2 = createWaypoint(id++,7, 16,i,j);
		Entity point3 = createWaypoint(id++,23, 31,i,j);
		Entity point4 = createWaypoint(id++,7, 31,i,j);
		waypointMapper.get(point2).activator = true;
		waypointMapper.get(point1).exit = true;
		
		waypointMapper.get(point1).neighbors.add(point3);
		waypointMapper.get(point4).neighbors.add(point2);
		waypointMapper.get(point2).neighbors.add(point1);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.PARK_TOP);
		mc.getInnerPoints().add(point1);
		mc.getInnerPoints().add(point2);
		mc.setTopInput(point4);
		mc.setTopOutput(point3);
		mc.setActivator(point2);
		mc.setExit(point1);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.PARK_TOP.toString()));
		
		Offset[] positions = new Offset[2];
		positions[0] = new Offset(-32, -32);
		point2.edit().add(new ParkingSpace(positions, point2));
		createFactoryMine(point2);
		return ent;
	}

	private static Entity createParkBot(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,23, 16,i,j);
		Entity point2 = createWaypoint(id++,7, 16,i,j);
		Entity point3 = createWaypoint(id++,23, 0,i,j);
		Entity point4 = createWaypoint(id++,7, 0,i,j);
		waypointMapper.get(point1).activator = true;
		waypointMapper.get(point2).exit = true;
		
		waypointMapper.get(point3).neighbors.add(point1);
		waypointMapper.get(point2).neighbors.add(point4);
		waypointMapper.get(point1).neighbors.add(point2);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.PARK_BOT);
		mc.getInnerPoints().add(point1);
		mc.getInnerPoints().add(point2);
		mc.setBotInput(point3);
		mc.setBotOutput(point4);
		mc.setActivator(point1);
		mc.setExit(point2);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.PARK_BOT.toString()));
		
		Offset[] positions = new Offset[2];
		positions[0] = new Offset(-32, 32);
		point1.edit().add(new ParkingSpace(positions, point2));
		createFactoryMine(point1);
		return ent;
	}

	private static Entity createTurnBotLeft(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,7, 0,i,j);
		Entity point2 = createWaypoint(id++,23, 0,i,j);
		Entity point3 = createWaypoint(id++,0, 7,i,j);
		Entity point4 = createWaypoint(id++,0, 23,i,j);
		Entity point5 = createWaypoint(id++,18, 18,i,j);
		
		waypointMapper.get(point2).neighbors.add(point5);
		waypointMapper.get(point5).neighbors.add(point4);
		waypointMapper.get(point3).neighbors.add(point1);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.TURN_BOT_LEFT);
		mc.getInnerPoints().add(point5);
		mc.setBotInput(point2);
		mc.setBotOutput(point1);
		mc.setLeftInput(point3);
		mc.setLeftOutput(point4);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.TURN_BOT_LEFT.toString()));
		return ent;
	}

	private static Entity createTurnBotRight(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,7, 0,i,j);
		Entity point2 = createWaypoint(id++,23, 0,i,j);
		Entity point3 = createWaypoint(id++,31, 7,i,j);
		Entity point4 = createWaypoint(id++,31, 23,i,j);
		Entity point5 = createWaypoint(id++,12, 18,i,j);
		
		waypointMapper.get(point2).neighbors.add(point3);
		waypointMapper.get(point4).neighbors.add(point5);
		waypointMapper.get(point5).neighbors.add(point1);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.TURN_BOT_RIGHT);
		mc.getInnerPoints().add(point5);
		mc.setBotInput(point2);
		mc.setBotOutput(point1);
		mc.setRightInput(point4);
		mc.setRightOutput(point3);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.TURN_BOT_RIGHT.toString()));
		return ent;
	}

	private static Entity createTurnTopLeft(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,7, 31,i,j);
		Entity point2 = createWaypoint(id++,23, 31,i,j);
		Entity point3 = createWaypoint(id++,0, 7,i,j);
		Entity point4 = createWaypoint(id++,0, 23,i,j);
		Entity point5 = createWaypoint(id++,18, 12,i,j);
		
		waypointMapper.get(point1).neighbors.add(point4);
		waypointMapper.get(point3).neighbors.add(point5);
		waypointMapper.get(point5).neighbors.add(point2);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.TURN_TOP_LEFT);
		mc.getInnerPoints().add(point5);
		mc.setTopInput(point1);
		mc.setTopOutput(point2);
		mc.setLeftInput(point3);
		mc.setLeftOutput(point4);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.TURN_TOP_LEFT.toString()));;
		return ent;
	}

	private static Entity createTurnTopRight(int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,7, 31,i,j);
		Entity point2 = createWaypoint(id++,23, 31,i,j);
		Entity point3 = createWaypoint(id++,31, 7,i,j);
		Entity point4 = createWaypoint(id++,31, 23,i,j);
		Entity point5 = createWaypoint(id++,12, 12,i,j);
		
		waypointMapper.get(point1).neighbors.add(point5);
		waypointMapper.get(point5).neighbors.add(point3);
		waypointMapper.get(point4).neighbors.add(point2);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(MapTile.TURN_TOP_RIGHT);
		mc.getInnerPoints().add(point5);
		mc.setTopInput(point1);
		mc.setTopOutput(point2);
		mc.setRightInput(point4);
		mc.setRightOutput(point3);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(MapTile.TURN_TOP_RIGHT.toString()));;
		return ent;
	}

	private static Entity createCrossroadMiddle(MapTile tile, int i, int j) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = null,point2 = null,point3 = null,point4 = null,point5 = null,point6 = null,point7 = null,point8 = null;
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(tile);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(tile.toString()));;
		if(tile.getId() != MapTile.CROSSROAD_MIDDLE_TOP.getId()) {
			point1 = createWaypoint(id++,7, 31,i,j);
			point2 = createWaypoint(id++,23, 31,i,j);
			mc.setTopInput(point1);
			mc.setTopOutput(point2);
		}
		if(tile.getId() != MapTile.CROSSROAD_MIDDLE_RIGHT.getId()) {
			point3 = createWaypoint(id++,31, 7,i,j);
			point4 = createWaypoint(id++,31, 23,i,j);
			mc.setRightInput(point4);
			mc.setRightOutput(point3);
		}
		if(tile.getId() != MapTile.CROSSROAD_MIDDLE_BOT.getId()) {
			point5 = createWaypoint(id++,7, 0,i,j);
			point6 = createWaypoint(id++,23, 0,i,j);
			mc.setBotInput(point6);
			mc.setBotOutput(point5);
		}
		if(tile.getId() != MapTile.CROSSROAD_MIDDLE_LEFT.getId()) {
			point7 = createWaypoint(id++,0, 7,i,j);
			point8 = createWaypoint(id++,0, 23,i,j);
			mc.setLeftInput(point7);
			mc.setLeftOutput(point8);
		}
		
		if(point1 != null && point5 != null)
			waypointMapper.get(point1).neighbors.add(point5);
		if(point1 != null && point3 != null)
			waypointMapper.get(point1).neighbors.add(point3);
		if(point1 != null && point8 != null)
			waypointMapper.get(point1).neighbors.add(point8);
		if(point4 != null && point8 != null)
			waypointMapper.get(point4).neighbors.add(point8);
		if(point4 != null && point2 != null)
			waypointMapper.get(point4).neighbors.add(point2);
		if(point4 != null && point5 != null)
			waypointMapper.get(point4).neighbors.add(point5);
		if(point6 != null && point2 != null)
			waypointMapper.get(point6).neighbors.add(point2);
		if(point6 != null && point3 != null)
			waypointMapper.get(point6).neighbors.add(point3);
		if(point6 != null && point8 != null)
			waypointMapper.get(point6).neighbors.add(point8);
		if(point7 != null && point3 != null)
			waypointMapper.get(point7).neighbors.add(point3);
		if(point7 != null && point5 != null)
			waypointMapper.get(point7).neighbors.add(point5);
		if(point7 != null && point2 != null)
			waypointMapper.get(point7).neighbors.add(point2);
		
		return ent;
	}

	private static Entity createVertical(int i, int j, MapTile type) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,7, 0,i,j);
		Entity point2 = createWaypoint(id++,23, 0,i,j);
		Entity point3 = createWaypoint(id++,7, 31,i,j);
		Entity point4 = createWaypoint(id++,23, 31,i,j);
		
		waypointMapper.get(point2).neighbors.add(point4);
		waypointMapper.get(point3).neighbors.add(point1);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(type);
		mc.setBotOutput(point1);
		mc.setBotInput(point2);
		mc.setTopInput(point3);
		mc.setTopOutput(point4);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(type.toString()));
		return ent;
	}

	private static Entity createHorizontal(int i, int j, MapTile type) {
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		Entity point1 = createWaypoint(id++,0, 7,i,j);
		Entity point2 = createWaypoint(id++,0, 23,i,j);
		Entity point3 = createWaypoint(id++,31, 7,i,j);
		Entity point4 = createWaypoint(id++,31, 23,i,j);
		
		waypointMapper.get(point1).neighbors.add(point3);
		waypointMapper.get(point4).neighbors.add(point2);
		
		Entity ent = Game.world.createEntity();
		RoadTile mc = new RoadTile(type);
		mc.setLeftInput(point1);
		mc.setLeftOutput(point2);
		mc.setRightInput(point4);
		mc.setRightOutput(point3);
		ent.edit().add(new Position(i*32+16, j*32+16)).add(mc).add(new TexReg(type.toString()));
		return ent;
	}
	
	private static Entity createWaypoint(int id, int offsetX, int offsetY, int i, int j) {
		Entity entity = Game.world.createEntity();
		entity.edit()
			.add(new Position(i*32 + offsetX, j*32 + offsetY))
			.add(new Waypoint(id));
		return entity;
	}
}
