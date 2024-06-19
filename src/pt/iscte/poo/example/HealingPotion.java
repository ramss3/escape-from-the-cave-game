package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class HealingPotion extends Pickable {
	
	private final int REGENERATE_LIFE = 5;

	public HealingPotion(Point2D position) {
		super(position, "HealingPotion", 7);
	}

	public int getREGENERATE_LIFE() {
		return REGENERATE_LIFE;
	}
	

}