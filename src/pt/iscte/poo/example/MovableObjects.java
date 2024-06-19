package pt.iscte.poo.example;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public abstract class MovableObjects extends GameElement implements Movable {
	

	private double hitPoints;
	private int damageGiven;
	
	public MovableObjects(Point2D position, String name, double hitPoints, int damageGiven, int layer) {
		super(position, name, layer);
		this.hitPoints = hitPoints;
		this.damageGiven = damageGiven;
		
	}
	
	/**
	 * Retorna os pontos de vida atuais do objeto.
	 * 
	 * @return os pontos de vida atuais
	 */	
	public double getHitPoints() {
		return this.hitPoints;
	}
	

	/**
	 * Define o dano causado pelo objeto.
	 * 
	 * @param damageGiven o dano causado pelo objeto
	 */
	public void setDamageGiven(int damageGiven) {
	    this.damageGiven = damageGiven;
	}

	/**
	 * Retorna o dano causado pelo objeto.
	 * 
	 * @return o dano causado pelo objeto
	 */
	public int getDamageGiven() {
	    return this.damageGiven;
	}

	/**
	 * Define os pontos de vida do objeto.
	 * 
	 * @param hitPoints os pontos de vida a serem definidos
	 */
	public void setHitPoints(double hitPoints) {
	    this.hitPoints = hitPoints;
	}
	
	/**
	 * Compara a posição atual do objeto com uma lista de pontos e retorna o ponto mais próximo.
	 * 
	 * @param list a lista de pontos a serem comparados
	 * @return o ponto mais próximo da posição atual do objeto
	 */	
	public Point2D compareTo(List<Point2D> list) {
		Point2D prevPosition = list.get(0);
		for(Point2D p: list) {

			if(prevPosition.distanceTo(getPosition()) > p.distanceTo(getPosition())) {
				prevPosition = p;
			}
		}
		return prevPosition;
	}
	
	/**
	 * Verifica se é possível mover para a posição especificada.
	 * 
	 * @param p a posição a ser verificada
	 * @return true se for possível mover para a posição, false caso contrário
	 */	
	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}
	
	
	
}
