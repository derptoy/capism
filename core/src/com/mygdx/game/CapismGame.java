package com.mygdx.game;


import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.component.Background;
import com.mygdx.game.component.Cargo;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Factory;
import com.mygdx.game.component.Mine;
import com.mygdx.game.component.ParkingSpace;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Route;
import com.mygdx.game.component.Route.State;
import com.mygdx.game.component.Storage;
import com.mygdx.game.component.Tex;
import com.mygdx.game.component.TexReg;
import com.mygdx.game.component.Waypoint;
import com.mygdx.game.component.ui.MouseFollow;
import com.mygdx.game.component.ui.SnapToGrid;
import com.mygdx.game.data.Offset;
import com.mygdx.game.data.RoadTile;
import com.mygdx.game.main.Game;
import com.mygdx.game.system.graphics.BackgroundSystem;
import com.mygdx.game.system.graphics.DriverTargetDrawSystem;
import com.mygdx.game.system.graphics.FactoryDrawSystem;
import com.mygdx.game.system.graphics.MineDrawSystem;
import com.mygdx.game.system.graphics.TextureRegionSystem;
import com.mygdx.game.system.graphics.TextureSystem;
import com.mygdx.game.system.graphics.WaypointSystem;
import com.mygdx.game.system.gui.MouseFollowSystem;
import com.mygdx.game.system.gui.SnapToGridSystem;
import com.mygdx.game.system.logic.DriverLogic;
import com.mygdx.game.system.logic.FactorySytem;
import com.mygdx.game.system.logic.LoadSystem;
import com.mygdx.game.system.logic.MineSystem;
import com.mygdx.game.system.logic.ParkingSystem;
import com.mygdx.game.system.logic.UnloadSystem;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.Astar;
import com.mygdx.game.system.passive.CameraSystem;
import com.mygdx.game.util.LevelLoader;
import com.mygdx.game.util.MapCells;
import com.mygdx.game.util.MapPoint;
import com.mygdx.game.util.MapTile;

public class CapismGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Astar astar;
	private Entity target1;
	private Entity target2;
	private int count;
	private RoadTile[][] roadTiles;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		// Managers
//		Game.world.setSystem(new GroupManager());
//		Game.world.setSystem(new TagManager());
		//				Game.world.setManager(new MatrixManager());
		//				Game.world.setManager(new FontManager());

		WorldConfiguration conf = new WorldConfiguration();
		conf.setManager(new TagManager());
		// Passive
		conf.setSystem(new AssetSystem());
//		conf.setSystem(new InputSystem());
		astar = new Astar();
		conf.setSystem(astar);
//		//
		// Active
		conf.setSystem(new CameraSystem());
		conf.setSystem(new DriverLogic());
		conf.setSystem(new MineSystem());
		conf.setSystem(new FactorySytem());
		conf.setSystem(new ParkingSystem());
		conf.setSystem(new LoadSystem());
		conf.setSystem(new UnloadSystem());
//		conf.setSystem(new ScheduleSystem());
//		conf.setSystem(new AttachedSystem());
//

		conf.setSystem(new MouseFollowSystem());
		conf.setSystem(new SnapToGridSystem());
		
//		conf.setSystem(new TriggerSystem());
//		conf.setSystem(new DoorSystem());
//		conf.setSystem(new WallSensorSystem());
//		conf.setSystem(new MouseSystem());
//		conf.setSystem(new AimSystem());

		// Graphics
		conf.setSystem(new BackgroundSystem());
		conf.setSystem(new WaypointSystem());
		conf.setSystem(new TextureSystem());
		conf.setSystem(new TextureRegionSystem());
		conf.setSystem(new FactoryDrawSystem());
		conf.setSystem(new MineDrawSystem());
		conf.setSystem(new DriverTargetDrawSystem());

		Game.world = new World(conf);
		
		fillWorld(Game.world.getManager(TagManager.class));
	}

	private void fillWorld(TagManager tagManager) {
//		Entity[][] mat = new Entity[5][5];
//		Waypoint[][] way = new Waypoint[5][5];
//		for (int i = 0; i < mat.length; i++) {
//			for (int j = 0; j < mat[i].length; j++) {
//				mat[i][j] = Game.world.createEntity();
//				Waypoint wp1 = new Waypoint();
//				mat[i][j].edit().add(new Position(150+80*(i), 150+80*(j))).add(wp1);
//				way[i][j] = wp1;
//			}
//		}
		
//		for (int i = 0; i < mat.length; i++) {
//			for (int j = 0; j < mat[i].length; j++) {
//				 int bot = j-1;
//				 int top = j+1;
//				 int left = i-1;
//				 int right = i+1;
//				 
//				 if(bot >= 0) {
//					 way[i][j].neighbors.add(mat[i][bot]);
//				 }
//				 if(top < mat.length) {
//					 way[i][j].neighbors.add(mat[i][top]);
//				 }
//				 if(left >= 0) {
//					 way[i][j].neighbors.add(mat[left][j]);
//				 }
//				 if(right < mat.length) {
//					 way[i][j].neighbors.add(mat[right][j]);
//				 }
//			}
//		}
		
//		Entity mineParking = Game.world.createEntity();
//		Position pos3 = new Position(100, 150+80*(3));
//		Tex tex3 = new Tex("factoryParking", -20, 10);
//		Waypoint wp3 = new Waypoint();
//		ParkingSpace space2 = new ParkingSpace(new int[][]{{-20,10}});
//		mineParking.edit().add(pos3).add(space2).add(tex3).add(wp3);
//		way[0][3].neighbors.add(mineParking);
//		wp3.neighbors.add(mat[0][3]);
//		Entity mine = Game.world.createEntity();
//		Mine min1 = new Mine(0, 1, 2, mineParking);
//		Tex texMine = new Tex("mine",0,50);
//		mine.edit().add(new Position(100, 150+80*(3))).add(min1).add(texMine);
//		
//		Entity from = mat[0][3];
//		Entity factoryParking = Game.world.createEntity();
//		Position pos2 = new Position(150+80*3, 100);
//		Tex tex2 = new Tex("factoryParking", 50, 0);
//		Waypoint wp2 = new Waypoint();
//		ParkingSpace space = new ParkingSpace(new int[][]{{50,50}});
//		factoryParking.edit().add(pos2).add(space).add(tex2).add(wp2);
//		way[3][0].neighbors.add(factoryParking);
//		wp2.neighbors.add(mat[3][0]);
		
//		Entity factory2 = Game.world.createEntity();
//		Factory fac2 = new Factory(0, 0, 1, 5, factoryParking);
//		Tex tex = new Tex("factory", 50, 0);
//		Position pos = new Position(150+80*3, 100);
//		factory2.edit().add(pos).add(fac2).add(tex);
		
		HashMap<Integer, SimpleEntry<Entity, MapPoint>> mapPoints = createMap();
		Object[] array = mapPoints.values().toArray();
		
//		@SuppressWarnings("unchecked")
//		SimpleEntry<Entity, MapPoint> entry = (SimpleEntry<Entity, MapPoint>)array[11];
		SimpleEntry<Entity, MapPoint> entry = mapPoints.get(417);
//		@SuppressWarnings("unchecked")
//		SimpleEntry<Entity, MapPoint> entry2 = (SimpleEntry<Entity, MapPoint>)array[30];
		
		LinkedList<Entity> path = astar.findPath(entry.getKey(), target1);
		Driver driver = new Driver();
		driver.turnRate = 300;
		driver.path=path;
		driver.target=entry.getKey();
		Route route = new Route(State.TRAVEL_LOAD);
		route.from = target1;
		route.to = target2;
		Cargo cargo = new Cargo();
		cargo.loadRate = 2;
		cargo.max=2;
		cargo.load=0;
		Entity entity = Game.world.createEntity();
		entity.edit()
			.add(new Position(600, 300))
			.add(new Tex("truck"))
			.add(driver)
			.add(cargo)
			.add(route)
			.add(new Dir(50));
		
		Entity testFollow = Game.world.createEntity();
		testFollow.edit()
			.add(new Position(0, 0))
			.add(new TexReg("test",0.7f))
			.add(new MouseFollow())
			.add(new SnapToGrid());
	}

	private HashMap<Integer,SimpleEntry<Entity,MapPoint>> createMap() {
		int offsetX = 0;
		int offsetY = -256;
		LevelLoader loader = new LevelLoader();
		loader.load("test.tmx");
		// Nejdriv zpracovat
		// Pak vytvorit entity
		// Nakonec je propojit
		
		roadTiles = new RoadTile[loader.getMapCells().length][loader.getMapCells()[0].length];
		
		// Odstranime duplicitni okrajove waypointy
		for (int i = 0; i < loader.getMapCells().length; i++) {
			for (int j = 0; j < loader.getMapCells()[i].length; j++) {
				MapCells mapCells = loader.getMapCells()[i][j];
				if(mapCells != null) {
					if(i+1 < loader.getMapCells().length && loader.getMapCells()[i+1][j] != null) {
						MapCells mapCells2 = loader.getMapCells()[i+1][j];
//						if(mapCells.getType() == MapTile.HORIZONTAL && mapCells2 != null && mapCells2.getType() == MapTile.HORIZONTAL) {
						// Na nasledujicim Tile odstranime left body a nahradime je right body s aktalniho Tile
						if(mapCells.getRightOutput() != null && mapCells.getRightInput() != null 
								&& mapCells2.getLeftInput() != null && mapCells2.getLeftOutput() != null ) {
							mapCells.getRightOutput().getNextList().addAll(mapCells2.getLeftInput().getNextList());
							mapCells2.setLeftInput(null);
							mapCells2.getLeftOutput().getNextList().addAll(mapCells.getRightInput().getNextList());
							mapCells.setRightInput(null);
						}
					}
					
					if(j+1 < loader.getMapCells()[i].length && loader.getMapCells()[i][j+1] != null) {
						MapCells mapCells2 = loader.getMapCells()[i][j+1];
//						if(mapCells.getType() == MapTile.VERTICAL && mapCells2 != null && mapCells2.getType() == MapTile.VERTICAL) {
						if(mapCells.getTopOutput() != null && mapCells.getTopInput() != null 
								&& mapCells2.getBotInput() != null && mapCells2.getBotOutput() != null ) {
							mapCells.getTopOutput().getNextList().addAll(mapCells2.getBotInput().getNextList());
							mapCells2.setBotInput(null);
							mapCells2.getBotOutput().getNextList().addAll(mapCells.getTopInput().getNextList());
							mapCells.setTopInput(null);
						}
					}
				}
			}
		}

		// Vytvorime waypointy pro policka
		HashMap<Integer,SimpleEntry<Entity, MapPoint>> mapPoints = new HashMap<>();
		for (int i = 0; i < loader.getMapCells().length; i++) {
			for (int j = 0; j < loader.getMapCells()[i].length; j++) {
				MapCells mapCells = loader.getMapCells()[i][j];
				if(mapCells != null) {
					roadTiles[i][j] = new RoadTile(mapCells.getType());
					
					// Vytvorime Waypoint entity a dame je do mapy
					processMapPoint(mapCells.getBotInput(),i,j,offsetX, offsetY, mapPoints);
					processMapPoint(mapCells.getBotOutput(),i,j,offsetX, offsetY, mapPoints);
					processMapPoint(mapCells.getTopInput(),i,j,offsetX, offsetY, mapPoints);
					processMapPoint(mapCells.getTopOutput(),i,j,offsetX, offsetY, mapPoints);
					processMapPoint(mapCells.getLeftInput(),i,j,offsetX, offsetY, mapPoints);
					processMapPoint(mapCells.getLeftOutput(),i,j,offsetX, offsetY, mapPoints);
					processMapPoint(mapCells.getRightInput(),i,j,offsetX, offsetY, mapPoints);
					processMapPoint(mapCells.getRightOutput(),i,j,offsetX, offsetY, mapPoints);
					processInnerPoints(mapCells.getInnerPoints(),i,j,offsetX, offsetY,mapPoints);
					
					if(mapCells.getBotInput() != null) {
						SimpleEntry<Entity, MapPoint> bi = mapPoints.get(mapCells.getBotInput().getId());
						roadTiles[i][j].setBotInput(bi.getKey());
					}
					if(mapCells.getBotOutput() != null) {
						SimpleEntry<Entity, MapPoint> bo = mapPoints.get(mapCells.getBotOutput().getId());
						roadTiles[i][j].setBotOutput(bo.getKey());
					}
					if(mapCells.getTopInput() != null) {
						SimpleEntry<Entity, MapPoint> ti = mapPoints.get(mapCells.getTopInput().getId());
						roadTiles[i][j].setTopInput(ti.getKey());
					}
					if(mapCells.getTopOutput() != null) {
						SimpleEntry<Entity, MapPoint> to = mapPoints.get(mapCells.getTopOutput().getId());
						roadTiles[i][j].setTopOutput(to.getKey());
					}
					if(mapCells.getLeftInput() != null) {
						SimpleEntry<Entity, MapPoint> li = mapPoints.get(mapCells.getLeftInput().getId());
						roadTiles[i][j].setLeftInput(li.getKey());
					}
					if(mapCells.getLeftOutput() != null) {
						SimpleEntry<Entity, MapPoint> lo = mapPoints.get(mapCells.getLeftOutput().getId());
						roadTiles[i][j].setLeftOutput(lo.getKey());
					}
					if(mapCells.getRightInput() != null) {
						SimpleEntry<Entity, MapPoint> ri = mapPoints.get(mapCells.getRightInput().getId());
						roadTiles[i][j].setRightInput(ri.getKey());
					}
					if(mapCells.getRightOutput() != null) {
						SimpleEntry<Entity, MapPoint> ro = mapPoints.get(mapCells.getRightOutput().getId());
						roadTiles[i][j].setRightOutput(ro.getKey());
					}
					
					if(mapCells.getType() == MapTile.PARK_BOT
							|| mapCells.getType() == MapTile.PARK_TOP
							|| mapCells.getType() == MapTile.PARK_LEFT
							|| mapCells.getType() == MapTile.PARK_RIGHT) {
						System.out.println("Parking "+mapCells.getType());
						MapPoint activator = null;
						MapPoint exit = null;
						for(MapPoint mp : mapCells.getInnerPoints()) {
							if(mp.isActivator())
								activator = mp;
							if(mp.isExit())
								exit = mp;
						}
						
						// Test vytvareni factory/mine
						SimpleEntry<Entity,MapPoint> simpleEntry = mapPoints.get(activator.getId());
						Entity ent = simpleEntry.getKey();
						Offset[] positions = new Offset[2];
						if(mapCells.getType() == MapTile.PARK_BOT)
							positions[0] = new Offset(-32, 32);
						else if(mapCells.getType() == MapTile.PARK_TOP)
							positions[0] = new Offset(-32, -32);
						else if(mapCells.getType() == MapTile.PARK_LEFT)
							positions[0] = new Offset(32, 32);
						else
							positions[0] = new Offset(-32, 32);
						ent.edit().add(new ParkingSpace(positions, mapPoints.get(exit.getId()).getKey()));
						
						if(target1 == null)
							target1 = ent;
						else if(target2 == null)
							target2 = ent;
						
						if(count%2==0) {
							Mine min1 = new Mine(1, 2);
							Tex texMine = new Tex("mine",0,50);
//							Position poss = new Position(offsetX+i*32, offsetY+j*32);
							Storage storage = new Storage();
							ent.edit().add(min1).add(texMine).add(storage);
						} else {
							Factory fac2 = new Factory(1, 5);
//							Position poss = new Position(offsetX+i*32, offsetY+j*32);
							Storage storage = new Storage();
							Tex tex = new Tex("factory", 50, 0);
							ent.edit().add(fac2).add(tex).add(storage);
						}
						count++;
					}
				}
			}
		}

		// Propojime Waypointy podle seznamu v Mappoint
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		for(Entry<Integer, SimpleEntry<Entity, MapPoint>> iemp : mapPoints.entrySet()) {
			for(int nextId : iemp.getValue().getValue().getNextList()) {
				SimpleEntry<Entity,MapPoint> simpleEntry = mapPoints.get(nextId);
				if(simpleEntry != null) {
					Waypoint waypoint = waypointMapper.get(iemp.getValue().getKey());
					waypoint.neighbors.add(simpleEntry.getKey());
				}
			}
		}
		
		Entity background = Game.world.createEntity();
		background.edit().add(new Position(offsetX,offsetY)).add(new Background(loader.getBackground()));
		
		return mapPoints;
	}

	private void processInnerPoints(List<MapPoint> points, int i, int j, int offsetX, int offsetY, HashMap<Integer, SimpleEntry<Entity, MapPoint>> mapPoints) {
		for(MapPoint point : points) {
			Entity cell = Game.world.createEntity();
			Waypoint ww = new Waypoint(point.getId());
			Position poss = new Position(offsetX+i*32 + point.getX(), offsetY+j*32 + point.getY());
			cell.edit().add(ww).add(poss);
	
			mapPoints.put(point.getId(),new SimpleEntry<>(cell, point));
		}
	}

	private void processMapPoint(MapPoint point, int i, int j, int offsetX, int offsetY, HashMap<Integer, SimpleEntry<Entity, MapPoint>> mapPoints) {
		if(point != null) {
			Entity cell = Game.world.createEntity();
			Waypoint ww = new Waypoint(point.getId());
			Position poss = new Position(offsetX+i*32 + point.getX(), offsetY+j*32 + point.getY());
			cell.edit().add(ww).add(poss);
	
			mapPoints.put(point.getId(),new SimpleEntry<>(cell, point));
			
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
  		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Game.world.setDelta(MathUtils.clamp(Gdx.graphics.getDeltaTime(),0, 1/15f));
        Game.world.process();
	}
	
}
