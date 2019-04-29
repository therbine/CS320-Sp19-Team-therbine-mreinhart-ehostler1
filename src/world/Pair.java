package world;

import java.io.Serializable;

public class Pair implements Serializable {
	private static final long serialVersionUID = 4L;
	
	private int x;
	private int y;

	public Pair(int x, int y) {
		this.x = x;
	    this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
