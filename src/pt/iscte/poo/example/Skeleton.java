package pt.iscte.poo.example;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Skeleton extends MovableObjects implements Updatable {
	public Skeleton(Point2D position) {
		super(position, "Skeleton", 5, 1, 6);
	}

	@Override
	public void update() {
		move(GameEngine.getInstance().getGui().keyPressed());
	}

	@Override
	public void move(int key) {

		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT) {
			if(GameEngine.getInstance().getTurns() % 2 == 0) {

				Direction dir = getPosition().directionTo(GameEngine.getInstance().getHero().getPosition());
				Vector2D randVector = dir.asVector(); 
				Point2D position = getPosition().plus(randVector);
				if(GameEngine.getInstance().isElementAtPosition(position, Hero.class)) {
					Hero hero = GameEngine.getInstance().getHero();
					GameEngine.getInstance().getHero().setHitPoints(hero.getHitPoints() - getDamageGiven());
					GameEngine.getInstance().getHero().setProporcionalLife();			
				} else if(canMoveTo(position) && !GameEngine.getInstance().isElementAtPosition(position, Wall.class) 
						&& !GameEngine.getInstance().isElementAtPosition(position, Skeleton.class) 
						&& !GameEngine.getInstance().isElementAtPosition(position, Door.class) 
						&& !GameEngine.getInstance().isElementAtPosition(position, Bat.class)) {
					setPosition(position);
				} else {
					List<Point2D> list = getPosition().getNeighbourhoodPoints();
					ArrayList<Point2D> aux = new ArrayList<>();
					for(Point2D p: list) {
						if(canMoveTo(p) && !GameEngine.getInstance().isElementAtPosition(p, Wall.class)) {
							aux.add(p);
						}
					}
					setPosition(compareTo(aux));

				}
			}

		}

	}

}
