package com.mygdx.game.component;

import com.artemis.Component;

public class TexReg extends Component {
	public String texture;
	public boolean flip;
	public int offsetX;
	public int offsetY;
	public float alpha;
	
	public TexReg(String texture) {
		this.texture = texture;
	}
	
	public TexReg(String texture, int offsetX, int offsetY, float alpha) {
		this.texture = texture;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.alpha = alpha;
	}

	public TexReg(String texture, float alpha) {
		this.texture = texture;
		this.alpha = alpha;
	}
}
