package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Key extends Pickable {
	
	String id;
	
	public Key(Point2D position, String id) {
		super(position, "Key", 3);
		this.id = id;
		
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


}
