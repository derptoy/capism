package com.mygdx.game.component;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class TexReg extends Component {
	public String texture;
	public boolean flip;
	public int offsetX;
	public int offsetY;
	public Color color = new Color(1f, 1f, 1f, 1f);
	
	public TexReg(String texture) {
		this.texture = texture;
	}
	
	public TexReg(String texture, Color color) {
		this.texture = texture;
		this.color = color;
	}
	
	public TexReg(String texture, int offsetX, int offsetY) {
		this.texture = texture;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
}
