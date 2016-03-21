package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.TexReg;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class TextureRegionSystem extends EntityProcessingSystem {

	private SpriteBatch batch;
	
	private CameraSystem cameraSystem;
	private AssetSystem assetSystem;
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<TexReg> textureMapper;
	private ComponentMapper<Dir> dirMapper;
	
	private ShapeRenderer shaperRenderer;

	@SuppressWarnings("unchecked")
	public TextureRegionSystem() {
		super(Aspect.all(Position.class,TexReg.class));
		this.batch = new SpriteBatch();
		
		shaperRenderer = new ShapeRenderer();
	}
	
	@Override
	protected void initialize() {
//		positionMapper = Game.world.getMapper(Position.class);
//		textureMapper = Game.world.getMapper(Tex.class);
//		assetSystem = Game.world.getSystem(AssetSystem.class);
//		cameraSystem = Game.world.getSystem(CameraSystem.class);
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		
	}
	
	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		TexReg tex = textureMapper.get(e);
		
		float angle = processFlip(e,tex);
		
		TextureRegion texture = assetSystem.getTextureRegion(tex.texture);
		float x = tex.offsetX + position.x - texture.getRegionWidth()/2;
		float y = tex.offsetY + position.y - texture.getRegionHeight()/2;
//		batch.setColor(0.7f, 1f, 0.7f, tex.alpha);
		batch.begin();
		batch.setColor(tex.color);
		batch.draw(texture, x, y);
		batch.setColor(1f,1f,1f,1f);
		batch.end();
//		shaperRenderer.setColor(Color.BLUE);
//		shaperRenderer.begin(ShapeType.Line);
//		shaperRenderer.rect(position.x - texture.getRegionWidth()/2, position.y - texture.getRegionHeight()/2, texture.getRegionWidth(), texture.getRegionHeight());
//		shaperRenderer.end();
	}
	
	@Override
	protected void end() {
		
	}

	private float processFlip(Entity e, TexReg tex) {
		Dir dir = dirMapper.get(e);
		
		float angle = 0;
		if(dir != null) {
			angle = dir.direction.angle();
			if(angle > 0 && angle < 90)
				tex.flip = false;
			else if(angle > 270 && angle <= 360)
				tex.flip = false;
			else
				tex.flip = true;
		}
		
		return angle;
	}

}
