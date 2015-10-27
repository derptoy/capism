package com.mygdx.game.component;

import com.artemis.Component;

public class Tex extends Component {
	public String texture;
	public boolean flip;
	public float rotation;
	
	public Tex(String texture) {
		this.texture = texture;
	}
}
