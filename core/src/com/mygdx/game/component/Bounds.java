package com.mygdx.game.component;

import com.artemis.Component;

public class Bounds extends Component {
	public int offsetX;
	public int offsetY;
	public int width;
	public int height;
	
	public Bounds(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
