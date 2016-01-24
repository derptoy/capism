package com.mygdx.game.component;

import com.artemis.Component;

public class Tex extends Component {
	public String texture;
	public boolean flip;
	public int offsetX;
	public int offsetY;
	
	public Tex(String texture) {
		this.texture = texture;
	}
	
	public Tex(String texture, int offsetX, int offsetY) {
		this.texture = texture;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
}
