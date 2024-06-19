package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Inventory extends GameElement {
	int inventoryColStart = 6;
	int inventoryColEnd = 9;
	boolean isInventoryFull = true;
	
	public Inventory(Point2D position) {
		super(position, "Black", 0);
	}

}
