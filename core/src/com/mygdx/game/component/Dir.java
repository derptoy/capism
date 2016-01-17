package com.mygdx.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Dir extends Component {
	public Vector2 direction = new Vector2(1, 0);
	public float speed = 0;
	
	public Dir(int speed) {
		this.speed = speed;
	}
}
