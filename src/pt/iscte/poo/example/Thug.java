package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Thug extends MovableObjects implements Updatable {
	public Thug(Point2D position) {
		super(position, "Thug", 10, 3, 7);
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public void move(int key) {
		
	}

}
