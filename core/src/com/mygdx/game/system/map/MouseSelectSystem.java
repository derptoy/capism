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
import com.mygdx.game.component.Position;
import com.mygdx.game.component.map.Selectable;
import com.mygdx.game.component.map.Selected;
import com.mygdx.game.component.ui.ObjectSelection;
import com.mygdx.game.main.Game;
import com.mygdx.game.system.logic.CollisionSystem;

@Wire
public class MouseSelectSystem extends EntityProcessingSystem {
	
	private ComponentMapper<ObjectSelection> objectSelectionMapper;
	
	private CollisionSystem collisionSystem;
	private TagManager tagManager;
	private boolean leftMousePressed;
	private int id;
	
	@SuppressWarnings("unchecked")
	public MouseSelectSystem() {
		super(Aspect.all(Position.class,Bounds.class,Selectable.class).exclude(Selected.class));
	}

	@Override
    protected void begin() {
        leftMousePressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched();
    }
	
	@Override
	protected void process(Entity e) {
		Entity mouse = tagManager.getEntity("mouse");
		
		ObjectSelection objectSelection = objectSelectionMapper.get(mouse);
		if(leftMousePressed && objectSelection != null) {
			if(collisionSystem.overlaps(mouse, e)) {
				Entity currentlySelected = Game.world.getEntity(id);
				if(currentlySelected != null) {
					currentlySelected.edit().remove(Selected.class);
				}
				
				id = e.getId();
				objectSelection.selected = e;
				e.edit().add(new Selected());
			}
		}
	}

}
