package com.mygdx.game.system.map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.component.Bounds;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Route;
import com.mygdx.game.component.map.Selectable;
import com.mygdx.game.component.ui.ObjectSelection;
import com.mygdx.game.component.ui.RouteSelection;
import com.mygdx.game.main.Game;
import com.mygdx.game.system.logic.CollisionSystem;
import com.mygdx.game.system.passive.Astar;

@Wire
public class RouteSelectSystem extends EntityProcessingSystem {
	
	private ComponentMapper<RouteSelection> routeSelectionMapper;
	private ComponentMapper<Route> routeMapper;
	private ComponentMapper<Driver> driverMapper;	
	
	private CollisionSystem collisionSystem;
	private TagManager tagManager;
	private boolean leftMousePressed;
	private Astar astar;
	
	public RouteSelectSystem () {
		super(Aspect.all(Position.class,Bounds.class,Selectable.class));
	}

	@Override
    protected void begin() {
        leftMousePressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched();
    }
	
	@Override
	protected void process(Entity e) {
		Entity mouse = tagManager.getEntity("mouse");
		
		if(leftMousePressed && routeSelectionMapper.has(mouse)) {
			RouteSelection selection = routeSelectionMapper.get(mouse);
			if(collisionSystem.overlaps(mouse, e)) {
				if(selection.from == null) {
					selection.from = e.id;
				} else {
					selection.to = e.id;
					Route route = routeMapper.get(selection.route);
					route.from = Game.world.getEntity(selection.from);
					route.to = Game.world.getEntity(selection.to);
					
					mouse.edit().remove(RouteSelection.class).add(new ObjectSelection());
					
					
					Driver driver = driverMapper.get(selection.route);
					if(driver.target != null)
						driver.path = astar.findPath(driver.target, route.to);
					else
						driver.path = astar.findPath(route.from, route.to);
				}
			}
		}
	}

}