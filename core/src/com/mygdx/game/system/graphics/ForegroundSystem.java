package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.component.Foreground;
import com.mygdx.game.component.Position;

@Wire
public class ForegroundSystem extends EntityProcessingSystem {

	private SpriteBatch batch;
	
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Foreground> foregroundMapper;

	public ForegroundSystem() {
		super(Aspect.all(Position.class,Foreground.class));
		this.batch = new SpriteBatch();
	}
	
	@Override
	protected void initialize() {
//		positionMapper = Game.world.getMapper(Position.class);
//		foregroundMapper = Game.world.getMapper(Foreground.class);
	}
	
	@Override
	protected void begin() {
		batch.begin();
	};
	
	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		Foreground foreground = foregroundMapper.get(e);
		
		float startx = position.x;// - foreground.layer.getWidth()*foreground.layer.getTileWidth()/2;
		float starty = position.y;// - foreground.layer.getHeight()*foreground.layer.getTileHeight()/2;
		for (int i = 0; i < foreground.layer.getWidth(); i++) {
			for (int j = 0; j < foreground.layer.getHeight(); j++) {
				Cell cell = foreground.layer.getCell(i, j);
				
				if(cell != null)
					batch.draw(cell.getTile().getTextureRegion(), 	startx + i*foreground.layer.getTileWidth(), 
																	starty + j*foreground.layer.getTileHeight());
			}
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
}
