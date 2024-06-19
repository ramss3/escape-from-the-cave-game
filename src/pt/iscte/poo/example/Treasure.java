package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Treasure extends GameElement {
		
		public Treasure(Point2D position) {
			super(position, "Treasure", 7);
		}

		@Override
		public String getName() {
			return "Treasure";
		}
		
		@Override
		public int getLayer() {
			return 7;
		}
		
		
	}
