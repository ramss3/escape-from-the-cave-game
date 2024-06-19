package pt.iscte.poo.example;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.example.GameElement;

public abstract class GameElement implements ImageTile {

	private Point2D position;
	private String name;
	private int layer;

	public GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;
	}

	/**
	 * Define a posição do objeto.
	 * 
	 * @param position a posição a ser definida
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	/**
	 * Retorna o nome do objeto.
	 * 
	 * @return o nome do objeto
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Retorna a camada do objeto.
	 * 
	 * @return a camada do objeto
	 */
	@Override
	public int getLayer() {
		return this.layer;
	}

	/**
	 * Define o nome do objeto.
	 * 
	 * @param name o nome do objeto
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Retorna a posição atual do objeto.
	 * 
	 * @return a posição atual do objeto
	 */
	@Override
	public Point2D getPosition() {
		return position;
	}


}
