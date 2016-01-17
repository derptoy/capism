package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Tex;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.CameraSystem;

@Wire
public class TextureSystem extends EntityProcessingSystem {

	private SpriteBatch batch;
	
	private CameraSystem cameraSystem;
	private AssetSystem assetSystem;
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Tex> textureMapper;
	private ComponentMapper<Dir> dirMapper;
	
//	private ShapeRenderer shaperRenderer;

	@SuppressWarnings("unchecked")
	public TextureSystem() {
		super(Aspect.all(Position.class,Tex.class));
		this.batch = new SpriteBatch();
		
//		shaperRenderer = new ShapeRenderer();
	}
	
	@Override
	protected void initialize() {
//		positionMapper = Game.world.getMapper(Position.class);
//		textureMapper = Game.world.getMapper(Tex.class);
//		assetSystem = Game.world.getSystem(AssetSystem.class);
//		cameraSystem = Game.world.getSystem(CameraSystem.class);
	}
	
	@Override
	protected void process(Entity e) {
		Position position = positionMapper.get(e);
		Tex tex = textureMapper.get(e);
		
		float angle = processFlip(e,tex);
		
		Texture texture = assetSystem.getTexture(tex.texture);
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
		batch.draw(texture, position.x - texture.getWidth()/2, position.y - texture.getHeight()/2,texture.getWidth()/2,texture.getHeight()/2,texture.getWidth(),texture.getHeight(),1,1,angle,0,0,texture.getWidth(),texture.getHeight(),false,tex.flip);
		batch.end();
		
//		shaperRenderer.setColor(Color.BLUE);
//		shaperRenderer.begin(ShapeType.Line);
//		shaperRenderer.rect(position.x - texture.getWidth()/2, position.y - texture.getHeight()/2, texture.getWidth(), texture.getHeight());
//		shaperRenderer.end();
		
		
	}

	private float processFlip(Entity e, Tex tex) {
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
