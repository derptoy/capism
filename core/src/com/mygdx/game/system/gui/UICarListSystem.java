package com.mygdx.game.system.gui;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Route;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class UICarListSystem extends EntityProcessingSystem {
	
	private int index;
	private AssetSystem assetSystem;
	private SpriteBatch batch;
	private CameraSystem cameraSystem;
	private ComponentMapper<Driver> driverMapper;
	private ComponentMapper<Route> routeMapper;

	public UICarListSystem() {
		super(Aspect.all(Driver.class, Route.class));
		batch = new SpriteBatch();
	}
	
	@Override
	protected void begin() {
		index = 0;
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		Driver driver = driverMapper.get(e);
		Route route = routeMapper.get(e);
		
		int x = 5;
		int y = 50 + 20*index;
		
		assetSystem.getDefaultFont().draw(batch, "Name: "+driver.name+", target: "+driver.target+",{"+route.from+" -> "+route.to+"  }", x, y);
		
		index++;
	}
	
	@Override
	protected void end() {
		batch.end();
	}

}
