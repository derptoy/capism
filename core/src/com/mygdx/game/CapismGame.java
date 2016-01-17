package com.mygdx.game;


import java.util.LinkedList;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.component.Dir;
import com.mygdx.game.component.Driver;
import com.mygdx.game.component.Factory;
import com.mygdx.game.component.Mine;
import com.mygdx.game.component.Position;
import com.mygdx.game.component.Tex;
import com.mygdx.game.component.Waypoint;
import com.mygdx.game.main.Game;
import com.mygdx.game.system.graphics.FactoryDrawSystem;
import com.mygdx.game.system.graphics.MineDrawSystem;
import com.mygdx.game.system.graphics.TextureSystem;
import com.mygdx.game.system.graphics.WaypointSystem;
import com.mygdx.game.system.logic.DriverLogic;
import com.mygdx.game.system.logic.FactorySytem;
import com.mygdx.game.system.logic.MineParkedSystem;
import com.mygdx.game.system.logic.MineSystem;
import com.mygdx.game.system.passive.AssetSystem;
import com.mygdx.game.system.passive.Astar;
import com.mygdx.game.system.passive.CameraSystem;

public class CapismGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Astar astar;
	
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
		conf.setManager(new TagManager());
		// Passive
		conf.setSystem(new AssetSystem());
//		conf.setSystem(new InputSystem());
		astar = new Astar();
		conf.setSystem(astar);
//		//
//		//				// Active
		conf.setSystem(new CameraSystem());
		conf.setSystem(new DriverLogic());
		conf.setSystem(new MineSystem());
		conf.setSystem(new FactorySytem());
		conf.setSystem(new MineParkedSystem());
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
		conf.setSystem(new WaypointSystem());
		conf.setSystem(new TextureSystem());
		conf.setSystem(new FactoryDrawSystem());
		conf.setSystem(new MineDrawSystem());

		Game.world = new World(conf);
		
		fillWorld(Game.world.getManager(TagManager.class));
	}

	private void fillWorld(TagManager tagManager) {
		Entity[][] mat = new Entity[5][5];
		Waypoint[][] way = new Waypoint[5][5];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				mat[i][j] = Game.world.createEntity();
				Waypoint wp1 = new Waypoint();
				mat[i][j].edit().add(new Position(150+80*(i), 150+80*(j))).add(wp1);
				way[i][j] = wp1;
			}
		}
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				 int bot = j-1;
				 int top = j+1;
				 int left = i-1;
				 int right = i+1;
				 
				 if(bot >= 0) {
					 way[i][j].neighbors.add(mat[i][bot]);
				 }
				 if(top < mat.length) {
					 way[i][j].neighbors.add(mat[i][top]);
				 }
				 if(left >= 0) {
					 way[i][j].neighbors.add(mat[left][j]);
				 }
				 if(right < mat.length) {
					 way[i][j].neighbors.add(mat[right][j]);
				 }
//				 if(bot >= 0 && right < 10) {
//					 way[i][j].neighbors.add(mat[right][bot]);
//				 }
//				 if(bot >= 0 && left >= 0) {
//					 way[i][j].neighbors.add(mat[left][bot]);
//				 }
//				 if(top < 10 && left >= 0) {
//					 way[i][j].neighbors.add(mat[left][top]);
//				 }
//				 if(top < 10 && right < 10) {
//					 way[i][j].neighbors.add(mat[right][top]);
//				 }
			}
		}
		
		Entity mine = Game.world.createEntity();
		Waypoint wp1 = new Waypoint();
		Mine min1 = new Mine(0, 1, 2);
		mine.edit().add(new Position(100, 150+80*(3))).add(wp1).add(min1);
		way[0][3].neighbors.add(mine);
		wp1.neighbors.add(mat[0][3]);
		
		Entity factory2 = Game.world.createEntity();
		Waypoint wp2 = new Waypoint();
		Factory fac2 = new Factory(0, 0, 1, 5);
		factory2.edit().add(new Position(150+80*3, 100)).add(wp2).add(fac2);
		way[3][0].neighbors.add(factory2);
		wp2.neighbors.add(mat[3][0]);
		
		tagManager.register("target", mine);
		tagManager.register("start", mat[0][0]);
		
		tagManager.register("factory", factory2);
		tagManager.register("mine", mine);
		
		LinkedList<Entity> path = astar.findPath(mat[0][0], mine);
		Driver driver = new Driver();
		driver.turnRate = 300;
		driver.loadRate = 2;
		driver.max=2;
		driver.path=path;
		driver.target=mat[0][0];
		Entity entity = Game.world.createEntity();
		entity.edit()
			.add(new Position(120, 120))
			.add(new Tex("truck"))
			.add(driver)
			.add(new Dir(50));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
  		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Game.world.setDelta(MathUtils.clamp(Gdx.graphics.getDeltaTime(),0, 1/15f));
        Game.world.process();
	}
	
}
