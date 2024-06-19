package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public abstract class Pickable extends GameElement {
	
	public Pickable(Point2D position, String name, int layer) {
		super(position, name, layer);
	}
	
	
}
