package com.mygdx.game;


import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.component.Attached;
import com.mygdx.game.component.Background;
import com.mygdx.game.component.Bounds;
import com.mygdx.game.component.Cargo;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Route;
import com.mygdx.game.component.Route.State;
import com.mygdx.game.component.Tex;
import com.mygdx.game.component.TexReg;
import com.mygdx.game.component.map.Map;
import com.mygdx.game.component.map.RoadTile;
import com.mygdx.game.component.map.Selectable;
import com.mygdx.game.component.map.Waypoint;
import com.mygdx.game.component.ui.Mouse;
import com.mygdx.game.component.ui.ObjectSelection;
import com.mygdx.game.component.ui.PlacebleTile;
import com.mygdx.game.component.ui.RouteSelection;
import com.mygdx.game.component.ui.SnapToGrid;
import com.mygdx.game.main.Game;
import com.mygdx.game.system.graphics.DriverTargetDrawSystem;
import com.mygdx.game.system.graphics.FactoryDrawSystem;
import com.mygdx.game.system.graphics.MineDrawSystem;
import com.mygdx.game.system.graphics.TextureRegionSystem;
import com.mygdx.game.system.graphics.TextureSystem;
import com.mygdx.game.system.graphics.WaypointSystem;
import com.mygdx.game.system.gui.AttachedSystem;
import com.mygdx.game.system.gui.MouseSystem;
import com.mygdx.game.system.gui.PutDownTileSystem;
import com.mygdx.game.system.gui.RouteSelectionSystem;
import com.mygdx.game.system.gui.SelectedSystem;
import com.mygdx.game.system.gui.SnapToGridSystem;
import com.mygdx.game.system.gui.TilePlaceabilitySystem;
import com.mygdx.game.system.gui.UICarListSystem;
import com.mygdx.game.system.logic.CollisionSystem;
import com.mygdx.game.system.logic.DriverLogic;
import com.mygdx.game.system.logic.FactorySytem;
import com.mygdx.game.system.logic.LoadSystem;
import com.mygdx.game.system.logic.MineSystem;
import com.mygdx.game.system.logic.ParkingSystem;
import com.mygdx.game.system.logic.UnloadSystem;
import com.mygdx.game.system.map.MapReplaceTileSystem;
import com.mygdx.game.system.map.MouseSelectSystem;
import com.mygdx.game.system.map.RouteSelectSystem;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.Astar;
import com.mygdx.game.system.passive.CameraSystem;
import com.mygdx.game.util.LevelLoader;
import com.mygdx.game.util.MapPoint;

public class CapismGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Astar astar;
	private Entity target1;
	private Entity target2;
	private int count;
	private Entity[][] roadTiles;
//	private RoadTile[][] roadTiles;
	
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
		conf.setManager(new GroupManager());
		// Passive
		conf.setSystem(new AssetSystem());
//		conf.setSystem(new InputSystem());
		astar = new Astar();
		conf.setSystem(astar);
//		//
		// Active
		conf.setSystem(new MouseSystem());
		conf.setSystem(new CameraSystem());
		conf.setSystem(new DriverLogic());
		conf.setSystem(new MineSystem());
		conf.setSystem(new FactorySytem());
		conf.setSystem(new ParkingSystem());
		conf.setSystem(new LoadSystem());
		conf.setSystem(new UnloadSystem());
		conf.setSystem(new MapReplaceTileSystem());
		conf.setSystem(new TilePlaceabilitySystem());
		conf.setSystem(new PutDownTileSystem());
//
		
		conf.setSystem(new CollisionSystem());

		conf.setSystem(new AttachedSystem());
		conf.setSystem(new SnapToGridSystem());
		
		// Graphics
//		conf.setSystem(new BackgroundSystem());
		conf.setSystem(new TextureRegionSystem());
		conf.setSystem(new TextureSystem());
		conf.setSystem(new WaypointSystem());
		conf.setSystem(new FactoryDrawSystem());
		conf.setSystem(new MineDrawSystem());
		conf.setSystem(new DriverTargetDrawSystem());
		conf.setSystem(new SelectedSystem());
		
		// UI
		conf.setSystem(new UICarListSystem());
		conf.setSystem(new MouseSelectSystem());
		conf.setSystem(new RouteSelectSystem());
		conf.setSystem(new RouteSelectionSystem());

		Game.world = new World(conf);
		
		fillWorld(Game.world.getManager(TagManager.class));
	}

	private void fillWorld(TagManager tagManager) {
		createMap();
//		SimpleEntry<Entity, MapPoint> entry = mapPoints.get(417);
		ComponentMapper<RoadTile> cm = Game.world.getMapper(RoadTile.class);
		ComponentMapper<Waypoint> wm = Game.world.getMapper(Waypoint.class);
		RoadTile roadTile = cm.get(roadTiles[3][15]);
		RoadTile roadTile2 = cm.get(roadTiles[3][16]);
		LinkedList<Entity> path = astar.findPath(roadTile.getRightOutput(), roadTile2.getTopOutput());
		Driver driver = new Driver("Car 1");
		driver.turnRate = 300;
		driver.path=path;
		driver.target = roadTile.getRightOutput();
		Route route = new Route(State.TRAVEL_LOAD);
		route.from = roadTile.getRightOutput();
		route.to = roadTile2.getTopOutput();
		Cargo cargo = new Cargo(2,2,0);
		Entity entity = Game.world.createEntity();
		entity.edit()
			.add(new Position(600, 300))
			.add(new Tex("truck"))
			.add(driver)
			.add(cargo)
			.add(route)
			.add(new Dir(50))
			.add(new Bounds(26,26))
			.add(new Selectable());
		
		RoadTile roadTile3 = cm.get(roadTiles[3][17]);
		GroupManager groupManager = Game.world.getManager(GroupManager.class);
		ImmutableBag<Entity> mines = groupManager.getEntities("mine");
		ImmutableBag<Entity> factories = groupManager.getEntities("factory");
		Random random = new Random();
		Entity randomMine = mines.get(random.nextInt(mines.size()));
		Entity randomFactory = factories.get(random.nextInt(factories.size()));
		
		System.out.println("M: "+randomMine);
		
		LinkedList<Entity> path2 = astar.findPath(roadTile.getRightOutput(), randomMine);
		Driver driver2 = new Driver("Car 2");
		driver2.turnRate = 300;
		driver2.path=path2;
		driver2.target = roadTile.getRightOutput();
		Route route2 = new Route(State.TRAVEL_LOAD);
		route2.from = randomMine;
		route2.to = randomFactory;
		Cargo cargo2 = new Cargo(2,2,0);
		Entity entity2 = Game.world.createEntity();
		entity2.edit()
			.add(new Position(600, 400))
			.add(new Tex("truck"))
			.add(driver2)
			.add(cargo2)
			.add(route2)
			.add(new Dir(50))
			.add(new Bounds(26,26))
			.add(new Selectable());
		
		Entity map = Game.world.createEntity();
		map.edit()
			.add(new Map(roadTiles));
		Game.world.getManager(TagManager.class).register("map", map);

		final Entity mouse=Game.world.createEntity();
		mouse.edit()
			.add(new Position(0, 0))
			.add(new Mouse())
			.add(new Bounds(8,8))
			.add(new ObjectSelection());
			//.add(new Tex("mouse"));
		Game.world.getManager(TagManager.class).register("mouse", mouse);
		
		Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
            	if(keycode == Keys.R) {
            		Entity testFollow = Game.world.createEntity();
            		testFollow.edit()
            			.add(new Position(0, 0))
            			.add(new TexReg("test", new Color(0.7f,1f,0.7f,0.7f)))
            			.add(new Attached(mouse))
            			.add(new SnapToGrid())
            			.add(new PlacebleTile());
            		Game.world.getManager(TagManager.class).register("follow", testFollow);
            	} else if(keycode == Keys.E) {
            		ComponentMapper<ObjectSelection> osm = Game.world.getMapper(ObjectSelection.class);
            		ObjectSelection objectSelection = osm.get(mouse);
					if(objectSelection != null) {
						Entity selected = objectSelection.selected;
            			mouse.edit()
            			.remove(ObjectSelection.class)
            			.add(new RouteSelection(selected));
            		}
            	}
            	return super.keyDown(keycode);
            }

        });
	}

	private void createMap() {
		int offsetX = 0;
		int offsetY = -256;
		LevelLoader loader = new LevelLoader();
		loader.load("test.tmx");
		AssetSystem assetSystem = Game.world.getSystem(AssetSystem.class);
		assetSystem.loadMapTexures(loader.getBackground());
		// Nejdriv zpracovat
		// Pak vytvorit entity
		// Nakonec je propojit
		
//		roadTiles = new RoadTile[loader.getMapCells().length][loader.getMapCells()[0].length];
		
		ComponentMapper<RoadTile> roadTileMapper = Game.world.getMapper(RoadTile.class);
		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
		roadTiles = loader.getRoadTile();
		
		// Odstranime duplicitni okrajove waypointy
		for (int i = 0; i < roadTiles.length; i++) {
			for (int j = 0; j < roadTiles[i].length; j++) {
				if(roadTiles[i][j] != null) {
					RoadTile mapCells = roadTileMapper.get(roadTiles[i][j]);
					if(i+1 < roadTiles.length && roadTiles[i+1][j] != null) {
						RoadTile mapCells2 =  roadTileMapper.get(roadTiles[i+1][j]);
//						if(mapCells.getType() == MapTile.HORIZONTAL && mapCells2 != null && mapCells2.getType() == MapTile.HORIZONTAL) {
						// Na nasledujicim Tile odstranime left body a nahradime je right body s aktalniho Tile
						if(mapCells.getRightOutput() != null && mapCells.getRightInput() != null 
								&& mapCells2.getLeftInput() != null && mapCells2.getLeftOutput() != null ) {
							waypointMapper.get(mapCells.getRightOutput()).neighbors.addAll(waypointMapper.get(mapCells2.getLeftInput()).neighbors);
							Game.world.deleteEntity(mapCells2.getLeftInput());
							mapCells2.setLeftInput(null);
							waypointMapper.get(mapCells2.getLeftOutput()).neighbors.addAll(waypointMapper.get(mapCells.getRightInput()).neighbors);
							Game.world.deleteEntity(mapCells.getRightInput());
							mapCells.setRightInput(null);
						}
					}
					
					if(j+1 < roadTiles[i].length && roadTiles[i][j+1] != null) {
						RoadTile mapCells2 = roadTileMapper.get(roadTiles[i][j+1]);
//						if(mapCells.getType() == MapTile.VERTICAL && mapCells2 != null && mapCells2.getType() == MapTile.VERTICAL) {
						if(mapCells.getTopOutput() != null && mapCells.getTopInput() != null 
								&& mapCells2.getBotInput() != null && mapCells2.getBotOutput() != null ) {
							waypointMapper.get(mapCells.getTopOutput()).neighbors.addAll(waypointMapper.get(mapCells2.getBotInput()).neighbors);
							Game.world.deleteEntity(mapCells2.getBotInput());
							mapCells2.setBotInput(null);
							waypointMapper.get(mapCells2.getBotOutput()).neighbors.addAll(waypointMapper.get(mapCells.getTopInput()).neighbors);
							Game.world.deleteEntity(mapCells.getTopInput());
							mapCells.setTopInput(null);
						}
					}
				}
			}
		}

		
		// Vytvorime waypointy pro policka
//		HashMap<Integer,SimpleEntry<Entity, MapPoint>> mapPoints = new HashMap<>();
//		for (int i = 0; i < loader.getMapCells().length; i++) {
//			for (int j = 0; j < loader.getMapCells()[i].length; j++) {
//				MapCells mapCells = loader.getMapCells()[i][j];
//				if(mapCells != null) {
//					TextureRegion textureRegion = loader.getBackground().getCell(i, j).getTile().getTextureRegion();
//					roadTiles[i][j] = new RoadTile(mapCells.getType());
//					roadTiles[i][j].setTextureRegion(textureRegion);
//					
//					Entity tile = Game.world.createEntity();
//					tile.edit()
//						.add(new Position(32*i + offsetX + 16, 32*j + offsetY + 16))
//						.add(roadTiles[i][j])
//						.add(new TexReg(mapCells.getType().toString()));
//					
//					// Vytvorime Waypoint entity a dame je do mapy
//					processMapPoint(mapCells.getBotInput(),i,j,offsetX, offsetY, mapPoints);
//					processMapPoint(mapCells.getBotOutput(),i,j,offsetX, offsetY, mapPoints);
//					processMapPoint(mapCells.getTopInput(),i,j,offsetX, offsetY, mapPoints);
//					processMapPoint(mapCells.getTopOutput(),i,j,offsetX, offsetY, mapPoints);
//					processMapPoint(mapCells.getLeftInput(),i,j,offsetX, offsetY, mapPoints);
//					processMapPoint(mapCells.getLeftOutput(),i,j,offsetX, offsetY, mapPoints);
//					processMapPoint(mapCells.getRightInput(),i,j,offsetX, offsetY, mapPoints);
//					processMapPoint(mapCells.getRightOutput(),i,j,offsetX, offsetY, mapPoints);
//					processInnerPoints(mapCells.getInnerPoints(),i,j,offsetX, offsetY,mapPoints);
//					
//					if(mapCells.getBotInput() != null) {
//						SimpleEntry<Entity, MapPoint> bi = mapPoints.get(mapCells.getBotInput().getId());
//						roadTiles[i][j].setBotInput(bi.getKey());
//					}
//					if(mapCells.getBotOutput() != null) {
//						SimpleEntry<Entity, MapPoint> bo = mapPoints.get(mapCells.getBotOutput().getId());
//						roadTiles[i][j].setBotOutput(bo.getKey());
//					}
//					if(mapCells.getTopInput() != null) {
//						SimpleEntry<Entity, MapPoint> ti = mapPoints.get(mapCells.getTopInput().getId());
//						roadTiles[i][j].setTopInput(ti.getKey());
//					}
//					if(mapCells.getTopOutput() != null) {
//						SimpleEntry<Entity, MapPoint> to = mapPoints.get(mapCells.getTopOutput().getId());
//						roadTiles[i][j].setTopOutput(to.getKey());
//					}
//					if(mapCells.getLeftInput() != null) {
//						SimpleEntry<Entity, MapPoint> li = mapPoints.get(mapCells.getLeftInput().getId());
//						roadTiles[i][j].setLeftInput(li.getKey());
//					}
//					if(mapCells.getLeftOutput() != null) {
//						SimpleEntry<Entity, MapPoint> lo = mapPoints.get(mapCells.getLeftOutput().getId());
//						roadTiles[i][j].setLeftOutput(lo.getKey());
//					}
//					if(mapCells.getRightInput() != null) {
//						SimpleEntry<Entity, MapPoint> ri = mapPoints.get(mapCells.getRightInput().getId());
//						roadTiles[i][j].setRightInput(ri.getKey());
//					}
//					if(mapCells.getRightOutput() != null) {
//						SimpleEntry<Entity, MapPoint> ro = mapPoints.get(mapCells.getRightOutput().getId());
//						roadTiles[i][j].setRightOutput(ro.getKey());
//					}
//					
//					if(mapCells.getType() == MapTile.PARK_BOT
//							|| mapCells.getType() == MapTile.PARK_TOP
//							|| mapCells.getType() == MapTile.PARK_LEFT
//							|| mapCells.getType() == MapTile.PARK_RIGHT) {
//						System.out.println("Parking "+mapCells.getType());
//						MapPoint activator = null;
//						MapPoint exit = null;
//						for(MapPoint mp : mapCells.getInnerPoints()) {
//							if(mp.isActivator())
//								activator = mp;
//							if(mp.isExit())
//								exit = mp;
//						}
//						
//						// Test vytvareni factory/mine
//						SimpleEntry<Entity,MapPoint> simpleEntry = mapPoints.get(activator.getId());
//						Entity ent = simpleEntry.getKey();
//						Offset[] positions = new Offset[2];
//						if(mapCells.getType() == MapTile.PARK_BOT)
//							positions[0] = new Offset(-32, 32);
//						else if(mapCells.getType() == MapTile.PARK_TOP)
//							positions[0] = new Offset(-32, -32);
//						else if(mapCells.getType() == MapTile.PARK_LEFT)
//							positions[0] = new Offset(32, 32);
//						else
//							positions[0] = new Offset(-32, 32);
//						ent.edit().add(new ParkingSpace(positions, mapPoints.get(exit.getId()).getKey()));
//						
//						if(target1 == null)
//							target1 = ent;
//						else if(target2 == null)
//							target2 = ent;
//						
//						if(count%2==0) {
//							Mine min1 = new Mine(1, 2);
//							Tex texMine = new Tex("mine",0,50);
////							Position poss = new Position(offsetX+i*32, offsetY+j*32);
//							Storage storage = new Storage();
//							ent.edit().add(min1).add(texMine).add(storage);
//						} else {
//							Factory fac2 = new Factory(1, 5);
////							Position poss = new Position(offsetX+i*32, offsetY+j*32);
//							Storage storage = new Storage();
//							Tex tex = new Tex("factory", 50, 0);
//							ent.edit().add(fac2).add(tex).add(storage);
//						}
//						count++;
//					}
//				}
//			}
//		}

		// Propojime Waypointy podle seznamu v Mappoint
//		ComponentMapper<Waypoint> waypointMapper = Game.world.getMapper(Waypoint.class);
//		for(Entry<Integer, SimpleEntry<Entity, MapPoint>> iemp : mapPoints.entrySet()) {
//			for(int nextId : iemp.getValue().getValue().neighbors) {
//				SimpleEntry<Entity,MapPoint> simpleEntry = mapPoints.get(nextId);
//				if(simpleEntry != null) {
//					Waypoint waypoint = waypointMapper.get(iemp.getValue().getKey());
//					waypoint.neighbors.add(simpleEntry.getKey());
//				}
//			}
//		}
		
		Entity background = Game.world.createEntity();
		background.edit().add(new Position(offsetX,offsetY)).add(new Background(loader.getBackground()));
		
//		return mapPoints;
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
