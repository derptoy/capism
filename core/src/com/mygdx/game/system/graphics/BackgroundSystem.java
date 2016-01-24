package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.component.Background;
import com.mygdx.game.component.Position;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class BackgroundSystem extends EntityProcessingSystem {

	private SpriteBatch batch;
	private AssetSystem assetSystem;
	private CameraSystem cameraSystem;
	
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Background> backgroundMapper;
	private Texture texture;

	public BackgroundSystem() {
		super(Aspect.all(Position.class,Background.class));
		this.batch = new SpriteBatch();
	}
	
	@Override
	protected void initialize() {
//		positionMapper = Game.world.getMapper(Position.class);
//		backgroundMapper = Game.world.getMapper(Background.class);
		texture = assetSystem.getTexture("factory");
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
	};
	
	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		Background background = backgroundMapper.get(e);
		
		float startx = position.x;// - background.layer.getWidth()*background.layer.getTileWidth()/2;
		float starty = position.y;// - background.layer.getHeight()*background.layer.getTileHeight()/2;
		for (int i = 0; i < background.layer.getWidth(); i++) {
			for (int j = 0; j < background.layer.getHeight(); j++) {
				Cell cell = background.layer.getCell(i, j);
				
				if(cell != null)
					batch.draw(cell.getTile().getTextureRegion(), 	startx + i*background.layer.getTileWidth(), 
																starty + j*background.layer.getTileHeight());
			}
		}
//		batch.draw(texture, 100,100);
	}
	
	@Override
	protected void end() {
		batch.end();
	}

}
