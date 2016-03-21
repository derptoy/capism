package com.mygdx.game.system.passive;

import java.util.HashMap;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.util.MapTile;

@Wire
public class AssetSystem extends BaseSystem {

	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private HashMap<String, TextureRegion> textureRegion = new HashMap<String, TextureRegion>();
	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	private BitmapFont font;
	
	@Override
	protected void initialize() {
		prepareTextures();
		prepareAnimations();
		prepareSound();
		prepareFonts();
	}
	
	private void prepareFonts() {
		font = new BitmapFont();
	}

	private void prepareSound() {
//		sounds.put("door1",Gdx.audio.newSound(Gdx.files.internal("sound/electric_door_opening_1.wav")));
//		sounds.put("door2",Gdx.audio.newSound(Gdx.files.internal("sound/electric_door_opening_2.wav")));
//		sounds.put("laser",Gdx.audio.newSound(Gdx.files.internal("sound/laser.wav")));
//		sounds.put("hit",Gdx.audio.newSound(Gdx.files.internal("sound/hit.wav")));
//		sounds.put("hit2",Gdx.audio.newSound(Gdx.files.internal("sound/hit2.wav")));
//		sounds.put("ambience",Gdx.audio.newSound(Gdx.files.internal("sound/ambience.wav")));
	}
	
	public void playSound(String sound) {
		sounds.get(sound).play();
	}
	
	public void loopSound(String sound, float volume) {
		sounds.get(sound).loop(volume);
	}

	private void prepareTextures() {
		loadTexture("truck2.png","truck");
		loadTexture("factory.png","factory");
		loadTexture("mine.png","mine");
		loadTexture("parkingSpace.png","factoryParking");
		loadTexture("street.png","street");
		loadTexture("mouse.png","mouse");
		
		loadTextureRegion("street","test",32*6,32*1,32,32);
	}

	private void loadTextureRegion(String fromTexture, String name, int x, int y, int width, int height) {
		Texture texture = textures.get(fromTexture);
		textureRegion.put(name, new TextureRegion(texture, x, y, width, height));
	}

	private void loadTexture(String url, String name) {
		Texture tr = new Texture(url);
		tr.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textures.put(name, tr);
	}

	private void prepareAnimations() {
//		Texture tex = new Texture(Gdx.files.internal("char/char2.png"));
////		TextureRegion[] keyFrames = splitTexture(tex,79, 158);
//		TextureRegion[] keyFrames = splitTexture(tex,32, 32);
//		
//		TextureRegion[] copyOfRange = new TextureRegion[3];
//		copyOfRange[0] = keyFrames[1];
//		copyOfRange[1] = keyFrames[2];
//		copyOfRange[2] = keyFrames[3];
//		Animation animation = new Animation(0.1f, copyOfRange);
//		animation.setPlayMode(PlayMode.LOOP);
//		animations.put("dummy_run", animation);
//		
//		TextureRegion[] idle = new TextureRegion[1];
//		idle[0] = keyFrames[0];	
//		Animation anim = new Animation(5, idle);
//		animation.setPlayMode(PlayMode.LOOP);
//		animations.put("dummy_idle", anim);
//		
//		tex = new Texture(Gdx.files.internal("door/door.png"));
//		TextureRegion[][] tmp = TextureRegion.split(tex, 8, 52);
//		TextureRegion[] doorOpen = new TextureRegion[3];
//		doorOpen[0] = tmp[0][0];
//		doorOpen[1] = tmp[0][1];
//		doorOpen[2] = tmp[0][2];
//		anim = new Animation(0.2f, doorOpen);
//		anim.setPlayMode(PlayMode.NORMAL);
//		animations.put("door_open", anim);
//		
//		TextureRegion[] doorClose = new TextureRegion[3];
//		doorClose[0] = tmp[0][2];
//		doorClose[1] = tmp[0][1];
//		doorClose[2] = tmp[0][0];
//		anim = new Animation(0.2f, doorClose);
//		anim.setPlayMode(PlayMode.NORMAL);
//		animations.put("door_close", anim);
//		
//		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("enemy/drone.txt"));
//		TextureRegion[] reg = new TextureRegion[6];
//		for(int i=0;i<6;i++)
//			reg[i] = atlas.findRegion("drone"+i);
//		anim = new Animation(0.2f, reg);
//		anim.setPlayMode(PlayMode.LOOP);
//		animations.put("drone_idle", anim);
	}

	private TextureRegion[] splitTexture(Texture tex, int width, int height) {
		TextureRegion[][] tmp = TextureRegion.split(tex, width, height);
		TextureRegion[] keyFrames = new TextureRegion[tmp.length*tmp[0].length];
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[0].length; j++) {
				keyFrames[tmp[0].length*i+j] = tmp[i][j];
			}
		}
		
		return keyFrames;
	}

	@Override
	protected void processSystem() {
	}
	
	public Animation getAnimation(String id) {
		return animations.get(id);
	}
	
	public Texture getTexture(String id) {
		return textures.get(id);
	}
	
	public BitmapFont getDefaultFont() {
		return font;
	}
	
	public TextureRegion getTextureRegion(String id) {
		return textureRegion.get(id);
	}

	public void loadMapTexures(TiledMapTileLayer background) {
		for(int i=0;i<background.getWidth();i++) {
			for (int j = 0; j < background.getHeight(); j++) {
				Cell cell = background.getCell(i, j);
				TiledMapTile tile = cell.getTile();
				if(tile != null) {
					MapTile mapTile = MapTile.getTileForId(tile.getId());
					if(mapTile != null)
						textureRegion.put(mapTile.toString(), tile.getTextureRegion());
				}
			}
		}
	}

}
