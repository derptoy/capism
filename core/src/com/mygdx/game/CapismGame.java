package com.mygdx.game;


import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Tex;
import com.mygdx.game.main.Game;
import com.mygdx.game.system.graphics.TextureSystem;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.CameraSystem;

public class CapismGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		// Managers
//		Game.world.setSystem(new GroupManager());
//		Game.world.setSystem(new TagManager());
		//				Game.world.setManager(new MatrixManager());
		//				Game.world.setManager(new FontManager());

		WorldConfiguration conf = new WorldConfiguration();
		// Passive
		conf.setSystem(new AssetSystem());
//		conf.setSystem(new InputSystem());
//		conf.setSystem(new MathUtil());
//		//
//		//				// Active
		conf.setSystem(new CameraSystem());
//		conf.setSystem(new GravitySystem());
//		conf.setSystem(new CollisionSystem());
//		conf.setSystem(new MoveSystem());
//		conf.setSystem(new ScheduleSystem());
//		conf.setSystem(new AttachedSystem());
//
//
//		conf.setSystem(new TriggerSystem());
//		conf.setSystem(new DoorSystem());
//		conf.setSystem(new WallSensorSystem());
//		conf.setSystem(new MouseSystem());
//		conf.setSystem(new AimSystem());

		//Graphics
//		conf.setSystem(new BackgroundSystem());
//		//				conf.setSystem(new SolidSystem());
//		conf.setSystem(new OneWayPlatformSystem());
//		conf.setSystem(new ClimbableSystem());
//		conf.setSystem(new AnimationSystem());
//		conf.setSystem(new ForegroundSystem());
		conf.setSystem(new TextureSystem());

		Game.world = new World(conf);
		
		Entity entity = Game.world.createEntity();
		entity.edit()
			.add(new Position(320, 240))
			.add(new Tex("test"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
  		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Game.world.setDelta(MathUtils.clamp(Gdx.graphics.getDeltaTime(),0, 1/15f));
        Game.world.process();
	}
}
