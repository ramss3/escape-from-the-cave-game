package pt.iscte.poo.example;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public abstract class Enemys extends GameElement {
	

	private double hitPoints;
	private int damageGiven;
	
	public Enemys(Point2D position, String name, double hitPoints, int damageGiven, int layer) {
		super(position, name, layer);
		this.hitPoints = hitPoints;
		this.damageGiven = damageGiven;
		
	}
	
	public double getHitPoints() {
		return this.hitPoints;
	}
	

	public void setDamageGiven(int damageGiven) {
		this.damageGiven = damageGiven;
	}

	public int getDamageGiven() {
		return this.damageGiven;
	}
	
	public void setHitPoints(double hitPoints) {
		this.hitPoints = hitPoints;
	}
	
	public Point2D compareTo(List<Point2D> list) {
		Point2D prevPosition = list.get(0);
		for(Point2D p: list) {

			if(prevPosition.distanceTo(getPosition()) > p.distanceTo(getPosition())) {
				prevPosition = p;
			}
		}
		return prevPosition;
	}
	
	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}
	
	
	
}
