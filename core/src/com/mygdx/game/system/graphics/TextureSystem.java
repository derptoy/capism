package com.mygdx.game.system.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
		
		if(tex.rotation > 0 && tex.rotation < 90)
			tex.flip = false;
		else if(tex.rotation > 270 && tex.rotation <= 360)
			tex.flip = false;
		else
			tex.flip = true;
		
		Texture texture = assetSystem.getTexture(tex.texture);
		batch.setProjectionMatrix(cameraSystem.camera.combined);
		batch.begin();
		batch.draw(texture, position.x - texture.getWidth()/2, position.y - texture.getHeight()/2,10,16,texture.getWidth(),texture.getHeight(),1,1,tex.rotation,0,0,texture.getWidth(),texture.getHeight(),false,tex.flip);
		batch.end();
		
//		shaperRenderer.setColor(Color.BLUE);
//		shaperRenderer.begin(ShapeType.Line);
//		shaperRenderer.rect(position.x - texture.getWidth()/2, position.y - texture.getHeight()/2, texture.getWidth(), texture.getHeight());
//		shaperRenderer.end();
		
		
	}

}
