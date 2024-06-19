package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement {
	String newRoom;
	int newRoomX;
	int newRoomY;
	String keyId;
	
	public Door(Point2D position, String newRoom, int newRoomX, int newRoomY, String keyId) {
		super(position, "DoorClosed", 7);
		this.newRoom = newRoom;
		this.newRoomX = newRoomX;
		this.newRoomY = newRoomY;
		this.keyId = keyId;
	}
	
	public Door(Point2D position, String newRoom, int newRoomX, int newRoomY) {
		super(position, "DoorClosed", 7);
		this.newRoom = newRoom;
		this.newRoomX = newRoomX;
		this.newRoomY = newRoomY;
	}

	public String getNewRoom() {
		return newRoom;
	}


	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public int getNewRoomX() {
		return newRoomX;
	}

	public int getNewRoomY() {
		return newRoomY;
	}


	public String getKeyId() {
		return keyId;
	}
	
}
