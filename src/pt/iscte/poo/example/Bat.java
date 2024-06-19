package pt.iscte.poo.example;

import java.awt.event.KeyEvent;
import java.util.Random;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends MovableObjects implements Updatable {

	public Bat(Point2D position) {
		super(position, "Bat", 3, 1, 3);
	}
	
	@Override
	public void update() {
		move(GameEngine.getInstance().getGui().keyPressed());
	}

	@Override
	public void move(int key) {
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP) {
			Random random = new Random();
			double randomNumber = random.nextDouble(); 

			if (randomNumber < 0.5) {    
				Direction dir = getPosition().directionTo(GameEngine.getInstance().getHero().getPosition());
				Vector2D randVector = dir.asVector(); 
				Point2D position = getPosition().plus(randVector);

				if(GameEngine.getInstance().isElementAtPosition(position, Hero.class)) {
					if (randomNumber < 0.5) {    
						Hero hero = GameEngine.getInstance().getHero();
						GameEngine.getInstance().getHero().setHitPoints(hero.getHitPoints() - getDamageGiven());
						GameEngine.getInstance().getHero().setProporcionalLife();
						if(getHitPoints() < 3)
							setHitPoints(getHitPoints()+1);
					}			
				} else if(canMoveTo(position) && !GameEngine.getInstance().isElementAtPosition(position, Wall.class) 
						&& !GameEngine.getInstance().isElementAtPosition(position, Skeleton.class)
						&& !GameEngine.getInstance().isElementAtPosition(position, Door.class) 
						&& !GameEngine.getInstance().isElementAtPosition(position, Bat.class)) {
					setPosition(position);
				}
			} else {
				Direction dir = Direction.random();
				Vector2D randVector = dir.asVector(); 
				Point2D position = getPosition().plus(randVector);
				if(GameEngine.getInstance().isElementAtPosition(position, Hero.class)) {
					if (randomNumber < 0.5) {    
						Hero hero = GameEngine.getInstance().getHero();
						GameEngine.getInstance().getHero().setHitPoints(hero.getHitPoints() - getDamageGiven());
						GameEngine.getInstance().getHero().setProporcionalLife();
						if(getHitPoints() < 3.0)
							setHitPoints(getHitPoints()+1);
					}
				} else if(canMoveTo(position) && !GameEngine.getInstance().isElementAtPosition(position, Wall.class) && !GameEngine.getInstance().isElementAtPosition(position, Skeleton.class)) {
					setPosition(position);
				}

			}
		}
	}

}
