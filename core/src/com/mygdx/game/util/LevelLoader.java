package com.mygdx.game.util;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LevelLoader {
	
	private TmxMapLoader loader;
	private TiledMapTileLayer foreground;
	private TiledMapTileLayer background;
	private MapCells[][] mapCells;
	private int id;

	public LevelLoader() {
		loader = new TmxMapLoader();
	}
	
	public void load(String url) {
		TiledMap map = loader.load(url);
		
		foreground = (TiledMapTileLayer)(map.getLayers().get("foreground"));
		background = (TiledMapTileLayer)(map.getLayers().get("background"));
		
		// Udelme matici s bodama pro jednotlive policka
		id = 0;
		mapCells = new MapCells[background.getWidth()][background.getHeight()];
		for (int i = 0; i < background.getWidth(); i++) {
			for (int j = 0; j < background.getHeight(); j++) {
				Cell cell = background.getCell(i, j);
				if(cell != null)
					processCell(cell, i, j);
			}
		}
		
		// Spojime body mezi policky a vytvotime sit
	}

	private void processCell(Cell cell, int i, int j) {
		int cellId = cell.getTile().getId();
		if(cellId == MapTile.VERTICAL.getId()) {
			createVertical(i,j);
		} else if(cellId == MapTile.HORIZONTAL.getId()) {
			createHorizontal(i,j);
		} else if(cellId == MapTile.TURN_BOT_RIGHT.getId()) {
			MapPoint point1 = new MapPoint(id++,7, 0);
			MapPoint point2 = new MapPoint(id++,23, 0);
			MapPoint point3 = new MapPoint(id++,31, 7);
			MapPoint point4 = new MapPoint(id++,31, 23);
			MapPoint point5 = new MapPoint(id++,12, 18);
			
			point2.getNextList().add(point3.getId());
			point4.getNextList().add(point5.getId());
			point5.getNextList().add(point1.getId());
			
			MapCells mc = new MapCells(MapTile.TURN_BOT_RIGHT);
			mc.getInnerPoints().add(point5);
			mc.setBotInput(point2);
			mc.setBotOutput(point1);
			mc.setRightInput(point4);
			mc.setRightOutput(point3);
			mapCells[i][j] = mc;
		} else if(cellId == MapTile.TURN_BOT_LEFT.getId()) {
			MapPoint point1 = new MapPoint(id++,7, 0);
			MapPoint point2 = new MapPoint(id++,23, 0);
			MapPoint point3 = new MapPoint(id++,0, 7);
			MapPoint point4 = new MapPoint(id++,0, 23);
			MapPoint point5 = new MapPoint(id++,18, 18);
			
			point2.getNextList().add(point5.getId());
			point5.getNextList().add(point4.getId());
			point3.getNextList().add(point1.getId());
			
			MapCells mc = new MapCells(MapTile.TURN_BOT_LEFT);
			mc.getInnerPoints().add(point5);
			mc.setBotInput(point2);
			mc.setBotOutput(point1);
			mc.setLeftInput(point3);
			mc.setLeftOutput(point4);
			mapCells[i][j] = mc;
		} else if(cellId == MapTile.TURN_TOP_RIGHT.getId()) {
			MapPoint point1 = new MapPoint(id++,7, 31);
			MapPoint point2 = new MapPoint(id++,23, 31);
			MapPoint point3 = new MapPoint(id++,31, 7);
			MapPoint point4 = new MapPoint(id++,31, 23);
			MapPoint point5 = new MapPoint(id++,12, 12);
			
			point1.getNextList().add(point5.getId());
			point5.getNextList().add(point3.getId());
			point4.getNextList().add(point2.getId());
			
			MapCells mc = new MapCells(MapTile.TURN_TOP_RIGHT);
			mc.getInnerPoints().add(point5);
			mc.setTopInput(point1);
			mc.setTopOutput(point2);
			mc.setRightInput(point4);
			mc.setRightOutput(point3);
			mapCells[i][j] = mc;
		} else if(cellId == MapTile.TURN_TOP_LEFT.getId()) {
			MapPoint point1 = new MapPoint(id++,7, 31);
			MapPoint point2 = new MapPoint(id++,23, 31);
			MapPoint point3 = new MapPoint(id++,0, 7);
			MapPoint point4 = new MapPoint(id++,0, 23);
			MapPoint point5 = new MapPoint(id++,18, 12);
			
			point1.getNextList().add(point4.getId());
			point3.getNextList().add(point5.getId());
			point5.getNextList().add(point2.getId());
			
			MapCells mc = new MapCells(MapTile.TURN_TOP_LEFT);
			mc.getInnerPoints().add(point5);
			mc.setTopInput(point1);
			mc.setTopOutput(point2);
			mc.setLeftInput(point3);
			mc.setLeftOutput(point4);
			mapCells[i][j] = mc;
		} else if(cellId == MapTile.CROSSROAD_LEFT.getId()) {
			createHorizontal(i,j);
		} else if(cellId == MapTile.CROSSROAD_RIGHT.getId()) {
			createHorizontal(i,j);
		} if(cellId == MapTile.CROSSROAD_TOP.getId()) {
			createVertical(i,j);
		}  if(cellId == MapTile.CROSSROAD_BOTTOM.getId()) {
			createVertical(i,j);
		}  else if((cellId == MapTile.CROSSROAD_MIDDLE.getId()
						|| cellId == MapTile.CROSSROAD_MIDDLE_TOP.getId()
						|| cellId == MapTile.CROSSROAD_MIDDLE_RIGHT.getId()
						|| cellId == MapTile.CROSSROAD_MIDDLE_BOT.getId()
						|| cellId == MapTile.CROSSROAD_MIDDLE_LEFT.getId())) {
			MapPoint point1 = null,point2 = null,point3 = null,point4 = null,point5 = null,point6 = null,point7 = null,point8 = null;
			MapCells mc = new MapCells(getTileType(cellId));
			mapCells[i][j] = mc;
			if(cellId != MapTile.CROSSROAD_MIDDLE_TOP.getId()) {
				point1 = new MapPoint(id++,7, 31);
				point2 = new MapPoint(id++,23, 31);
				mc.setTopInput(point1);
				mc.setTopOutput(point2);
			}
			if(cellId != MapTile.CROSSROAD_MIDDLE_RIGHT.getId()) {
				point3 = new MapPoint(id++,31, 7);
				point4 = new MapPoint(id++,31, 23);
				mc.setRightInput(point4);
				mc.setRightOutput(point3);
			}
			if(cellId != MapTile.CROSSROAD_MIDDLE_BOT.getId()) {
				point5 = new MapPoint(id++,7, 0);
				point6 = new MapPoint(id++,23, 0);
				mc.setBotInput(point6);
				mc.setBotOutput(point5);
			}
			if(cellId != MapTile.CROSSROAD_MIDDLE_LEFT.getId()) {
				point7 = new MapPoint(id++,0, 7);
				point8 = new MapPoint(id++,0, 23);
				mc.setLeftInput(point7);
				mc.setLeftOutput(point8);
			}
			
			if(point1 != null && point5 != null)
				point1.getNextList().add(point5.getId());
			if(point1 != null && point3 != null)
				point1.getNextList().add(point3.getId());
			if(point1 != null && point8 != null)
				point1.getNextList().add(point8.getId());
			if(point4 != null && point8 != null)
				point4.getNextList().add(point8.getId());
			if(point4 != null && point2 != null)
				point4.getNextList().add(point2.getId());
			if(point4 != null && point5 != null)
				point4.getNextList().add(point5.getId());
			if(point6 != null && point2 != null)
				point6.getNextList().add(point2.getId());
			if(point6 != null && point3 != null)
				point6.getNextList().add(point3.getId());
			if(point6 != null && point8 != null)
				point6.getNextList().add(point8.getId());
			if(point7 != null && point3 != null)
				point7.getNextList().add(point3.getId());
			if(point7 != null && point5 != null)
				point7.getNextList().add(point5.getId());
			if(point7 != null && point2 != null)
				point7.getNextList().add(point2.getId());
		} else if(cellId == MapTile.PARK_BOT.getId()) {
			MapPoint point1 = new MapPoint(id++,23, 16);
			MapPoint point2 = new MapPoint(id++,7, 16);
			MapPoint point3 = new MapPoint(id++,23, 0);
			MapPoint point4 = new MapPoint(id++,7, 0);
			point1.setActivator(true);
			point2.setExit(true);
			
			point3.getNextList().add(point1.getId());
			point2.getNextList().add(point4.getId());
			point1.getNextList().add(point2.getId());
			
			MapCells mc = new MapCells(MapTile.PARK_BOT);
			mc.getInnerPoints().add(point1);
			mc.getInnerPoints().add(point2);
			mc.setBotInput(point3);
			mc.setBotOutput(point4);
			mapCells[i][j] = mc;
		} else if(cellId == MapTile.PARK_TOP.getId()) {
			MapPoint point1 = new MapPoint(id++,23, 16);
			MapPoint point2 = new MapPoint(id++,7, 16);
			MapPoint point3 = new MapPoint(id++,23, 31);
			MapPoint point4 = new MapPoint(id++,7, 31);
			point2.setActivator(true);
			point1.setExit(true);
			
			point1.getNextList().add(point3.getId());
			point4.getNextList().add(point2.getId());
			point2.getNextList().add(point1.getId());
			
			MapCells mc = new MapCells(MapTile.PARK_TOP);
			mc.getInnerPoints().add(point1);
			mc.getInnerPoints().add(point2);
			mc.setTopInput(point4);
			mc.setTopOutput(point3);
			mapCells[i][j] = mc;
		} else if(cellId == MapTile.PARK_LEFT.getId()) {
			MapPoint point1 = new MapPoint(id++,16, 23);
			MapPoint point2 = new MapPoint(id++,16, 7);
			MapPoint point3 = new MapPoint(id++,0, 23);
			MapPoint point4 = new MapPoint(id++,0, 7);
			point2.setActivator(true);
			point1.setExit(true);
			
			point1.getNextList().add(point3.getId());
			point4.getNextList().add(point2.getId());
			point2.getNextList().add(point1.getId());
			
			MapCells mc = new MapCells(MapTile.PARK_LEFT);
			mc.getInnerPoints().add(point1);
			mc.getInnerPoints().add(point2);
			mc.setLeftInput(point4);
			mc.setLeftOutput(point3);
			mapCells[i][j] = mc;
		} else if(cellId == MapTile.PARK_RIGHT.getId()) {
			MapPoint point1 = new MapPoint(id++,16, 23);
			MapPoint point2 = new MapPoint(id++,16, 7);
			MapPoint point3 = new MapPoint(id++,31, 23);
			MapPoint point4 = new MapPoint(id++,31, 7);
			point1.setActivator(true);
			point2.setExit(true);
			
			point3.getNextList().add(point1.getId());
			point2.getNextList().add(point4.getId());
			point1.getNextList().add(point2.getId());
			
			MapCells mc = new MapCells(MapTile.PARK_RIGHT);
			mc.getInnerPoints().add(point1);
			mc.getInnerPoints().add(point2);
			mc.setRightInput(point3);
			mc.setRightOutput(point4);
			mapCells[i][j] = mc;
		}
	}

	private MapTile getTileType(int cellId) {
		for(MapTile tile : MapTile.values()) {
			if(cellId == tile.getId())
				return tile;
		}
		return null;
	}

	private void createVertical(int i, int j) {
		MapPoint point1 = new MapPoint(id++,7, 0);
		MapPoint point2 = new MapPoint(id++,23, 0);
		MapPoint point3 = new MapPoint(id++,7, 31);
		MapPoint point4 = new MapPoint(id++,23, 31);
		
		point2.getNextList().add(point4.getId());
		point3.getNextList().add(point1.getId());
		
		MapCells mc = new MapCells(MapTile.VERTICAL);
		mc.setBotOutput(point1);
		mc.setBotInput(point2);
		mc.setTopInput(point3);
		mc.setTopOutput(point4);
		mapCells[i][j] = mc;
	}

	private void createHorizontal(int i, int j) {
		MapPoint point1 = new MapPoint(id++,0, 7);
		MapPoint point2 = new MapPoint(id++,0, 23);
		MapPoint point3 = new MapPoint(id++,31, 7);
		MapPoint point4 = new MapPoint(id++,31, 23);
		
		point1.getNextList().add(point3.getId());
		point4.getNextList().add(point2.getId());
		
		MapCells mc = new MapCells(MapTile.HORIZONTAL);
		mc.setLeftInput(point1);
		mc.setLeftOutput(point2);
		mc.setRightInput(point4);
		mc.setRightOutput(point3);
		mapCells[i][j] = mc;
	}

	public TiledMapTileLayer getForeground() {
		return foreground;
	}

	public TiledMapTileLayer getBackground() {
		return background;
	}

	public MapCells[][] getMapCells() {
		return mapCells;
	}

	
}
