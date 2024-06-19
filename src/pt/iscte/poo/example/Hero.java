package pt.iscte.poo.example;

import java.awt.event.KeyEvent;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Hero extends MovableObjects implements Movable {

	private final int MAX_LIFE = 10;

	public Hero(Point2D position) {
		super(position, "Hero", 10, 1, 10);
	}

	@Override
	public void move(int key) {
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT) {
			Direction dir = Direction.directionFor(key);
			Vector2D randVector = dir.asVector(); 
			Point2D newPosition = getPosition().plus(randVector);
			MovableObjects enemy = (MovableObjects) GameEngine.getInstance().getElementAtPosition(newPosition, MovableObjects.class);

			if(canMoveTo(newPosition) && !GameEngine.getInstance().isElementAtPosition(newPosition, Wall.class) && !GameEngine.getInstance().isElementAtPosition(newPosition, MovableObjects.class)) {
				setPosition(newPosition);
			} else if(GameEngine.getInstance().isElementAtPosition(newPosition, MovableObjects.class)) {
				double hitPoints = enemy.getHitPoints();
				hitPoints -= getDamageGiven();
				enemy.setHitPoints(hitPoints);

				if(enemy.getHitPoints() <= 0) {
					GameEngine.getInstance().removeImage(enemy);
				}
			}

			if(GameEngine.getInstance().isElementAtPosition(newPosition, Sword.class)) {
				setDamageGiven(getDamageGiven()*2);
			}


			if(GameEngine.getInstance().isElementAtPosition(newPosition, Pickable.class)) {
				for(int x = 7; x < 10; x++) {
					Point2D position = new Point2D(x,10);
					if(GameEngine.getInstance().getCatchableObjAtPosition(position) == null) {
						((GameElement) GameEngine.getInstance().getElementAtPosition(newPosition, Pickable.class)).setPosition(position);
						GameEngine.getInstance().getInventoryList().add(GameEngine.getInstance().getElementAtPosition(position, Pickable.class));
						GameEngine.getInstance().getTileList().remove(GameEngine.getInstance().getElementAtPosition(position, Pickable.class));
						break;
					}
				}
			}

			if(GameEngine.getInstance().isElementAtPosition(newPosition, Door.class)) {
				for(int i = 7; i < 10; i++) {
					Point2D position = new Point2D(i,10);
					if(GameEngine.getInstance().getKeyAtPosition(position) != null && GameEngine.getInstance().getKeyAtPosition(position).getId().equals(GameEngine.getInstance().getDoorAtPosition(newPosition).getKeyId()) ) {
						newRoom(newPosition);					
						return;
					}
				}
				if(GameEngine.getInstance().getDoorAtPosition(newPosition).getKeyId().equals("")) {
					newRoom(newPosition);
				}
			}
			if(GameEngine.getInstance().isElementAtPosition(newPosition, Treasure.class)) {
				GameEngine.getInstance().getGui().setMessage("You Won!");
			}
		}
	}

	public void newRoom(Point2D newPosition) {
		String sala = GameEngine.getInstance().getDoorAtPosition(newPosition).getNewRoom();
		int x = GameEngine.getInstance().getDoorAtPosition(newPosition).getNewRoomX();
		int y = GameEngine.getInstance().getDoorAtPosition(newPosition).getNewRoomY();
		setPosition(new Point2D(x,y));
		Hero hero = GameEngine.getInstance().getHero();						
		GameEngine.getInstance().removeList(newList());
		GameEngine.getInstance().getTileList().add(hero);
		GameEngine.getInstance().addFloor("rooms\\" + sala + ".txt");
		GameEngine.getInstance().createObjects("rooms\\" + sala + ".txt");
		GameEngine.getInstance().getDoorAtPosition(getPosition()).setName("DoorOpen");;
	}

	public List<ImageTile> newList()  {
		List<ImageTile> newList = GameEngine.getInstance().getTileList();
		return newList;
	}

	public void setProporcionalLife() {
		double number = getHitPoints() *  GameEngine.getInstance().getLifeList().size() / MAX_LIFE;
		double aux = (double) Math.round(number * 2) / 2;
		List<ImageTile> list = GameEngine.getInstance().getLifeList();
		if( Math.ceil(aux) >= 0) {
			if((Math.floor(aux) == aux) && (Math.ceil(aux) == aux)) {
				for(int i = list.size() - 1; i >= aux; i--) {
					((GameElement) list.get(i)).setName("Red");
				} 
			} else {
				for(int i = list.size() - 1; i >= Math.ceil(aux); i--) {
					((GameElement) list.get(i)).setName("Red");
				}
				if((int)Math.floor(aux) >= 0) {
					((GameElement) list.get((int)Math.floor(aux))).setName("GreenRed");
				}

			} 
		}
	}

	public void setProporcionalLifeIncreased() {
		double number = getHitPoints() * GameEngine.getInstance().getLifeList().size() / MAX_LIFE;
		double aux = (double) Math.round(number * 2) / 2;
		double n2 = GameEngine.getInstance().getHealingPotion().getREGENERATE_LIFE() * 7 / MAX_LIFE;
		double aux2 = (double) Math.round(n2 * 2) / 2;
		double newLife = aux + aux2;
		if(newLife > GameEngine.getInstance().getLifeList().size()) {
			newLife = GameEngine.getInstance().getLifeList().size();
		}

		List<ImageTile> list = GameEngine.getInstance().getLifeList();			

		for (int i = 0; i < (int) Math.floor(newLife); i++) {
			((GameElement) list.get(i)).setName("Green");
		}
		for(int y = (int) Math.floor(newLife); y < list.size(); y++) {
			((GameElement) list.get(y)).setName("Red");
		}
	}
}
