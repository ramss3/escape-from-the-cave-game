package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;


public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 11;
	public static final int GRID_WIDTH = 10;
	private static GameEngine INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	private Hero hero = new Hero(new Point2D(1,1));
	private Skeleton skeleton;
	private Bat bat;
	private Sword sword;
	private Key key;
	private Door door;
	private Wall wall;
	private Floor floor;
	private Life life;
	private Inventory inventory;
	private HealingPotion healingPotion;
	private Treasure treasure;
	private Thug thug;
	private Armor armor;
	private List<ImageTile> tileList = new ArrayList<>();
	private List<ImageTile> lifeList = new ArrayList<>();
	private List<ImageTile> inventoryList = new ArrayList<>();
	private int turns;

	/**
	 * @return a poção de cura
	 */
	public HealingPotion getHealingPotion() {
		return healingPotion;
	}

	/**
	 * @return a lista de ImageTiles para a vida do jogador
	 */
	public List<ImageTile> getLifeList() {
	    return lifeList;
	}

	/**
	 * Retorna a lista de ImageTiles para os Tiles.
	 *
	 * @return a lista de ImageTiles para os Tiles
	 */
	public List<ImageTile> getTileList() {
	    return tileList;
	}

	/**
	 * Retorna a lista de ImageTiles para o inventário do jogador.
	 *
	 * @return a lista de ImageTiles para o inventário do jogador
	 */
	public List<ImageTile> getInventoryList() {
	    return inventoryList;
	}

	/**
	 * Retorna a instância do objeto ImageMatrixGUI.
	 *
	 * @return a instância do objeto ImageMatrixGUI
	 */
	public ImageMatrixGUI getGui() {
	    return gui;
	}

	/**
	 * Retorna o objeto Hero do jogo.
	 *
	 * @return o objeto Hero do jogo
	 */
	public Hero getHero() {
	    return hero;
	}

	/**
	 * Retorna o número de turnos decorridos no jogo.
	 *
	 * @return o número de turnos decorridos no jogo
	 */
	public int getTurns() {
	    return turns;
	}

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	private GameEngine() {		
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT);
		gui.go();
	}
	
	/**
	 * Atualiza o estado do jogo com base em uma fonte observada.
	 * Se uma tecla de movimento for pressionada (setas), o herói move-se na direção correspondente
	 * e o contador de turnos é incrementado.
	 * Se a tecla "T" for pressionada e a poção de cura estiver no inventário do hero, a ação de tomar a poção é executada, 
	 * removendo a healingPotion do inventário.
	 * Em seguida, atualiza todos os elementos que implementam a interface Updatable.
	 * Se o herói ficar sem pontos de vida, exibe uma mensagem de "Game Over".
	 * Atualiza a mensagem de status na interface gráfica e a interface gráfica em si.
	 *
	 * @param source a fonte observada que acionou a atualização.
	 */
	@Override
	public void update(Observed source) {

		int key = ((ImageMatrixGUI) source).keyPressed();

		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP) {		
			hero.move(key);
			turns++;
		}


		if(key == KeyEvent.VK_T && inventoryList.contains(healingPotion)) {
			inventoryList.remove(healingPotion);
			gui.removeImage(healingPotion);
			takePotion();

		}

		for(Updatable up: getUpdatables()) {
			up.update();
		}

		if(hero.getHitPoints() <= 0) { 
			gui.setMessage("Game Over!");
		}

		gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
		gui.update();
	}


	/**
	 * Envia as imagens da tileList para a interface gráfica.
	 */
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
	
	
	/**
	 * Remove uma lista de ImageTiles da interface gráfica e da lista de tiles.
	 *
	 * @param list a lista de ImageTiles a ser removida
	 */
	public void removeList(final List<ImageTile> list) {
		gui.removeImages(list); 
		tileList.removeAll(list);
		gui.update();
	}
	
	/**
	 * Inicia o jogo. Adiciona o herói à lista de tiles, carrega o arquivo do cenário inicial,
	 * cria as listas necessárias e os objetos do cenário, atualiza a mensagem de status na interface gráfica
	 * e realiza a atualização da interface gráfica.
	 */
	public void start() {
		tileList.add(hero);
		addFloor("rooms\\room0.txt");
		creatingLists();
		createObjects("rooms\\room0.txt");
		gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
		gui.update();
	}
	
	/**
	 * Executa a ação de tomar a poção. Aumenta proporcionalmente a vida do herói,
	 * se os pontos de vida do herói forem menores que 10, adiciona 5 pontos de vida,
	 * garantindo que não ultrapasse o limite de 10 hitPoints.
	 */	
	public void takePotion() {
		
		getHero().setProporcionalLifeIncreased();
		if(getHero().getHitPoints() < 10) {
			double aux = getHero().getHitPoints() + 5;
			if(aux > 10) {
				getHero().setHitPoints(10);
			} else {
				getHero().setHitPoints(aux);
			}
			
		}
	}

	/**
	 * Cria as listas de vida e inventário, adicionando as ImageTiles correspondentes.
	 */
	public void creatingLists() {
		for (int x=0; x<GRID_WIDTH; x++) {
			if(x <= 6) {
				life = new Life(new Point2D(x,10));
				lifeList.add(life);

			} else {
				inventory = new Inventory(new Point2D(x,10));
				inventoryList.add(inventory);
			}
		}
		gui.addImages(lifeList);
		gui.addImages(inventoryList);

	}
	
	/**
	 * Retorna uma lista de objetos Updatable presentes na tileList.
	 *
	 * @return a lista de objetos Updatable
	 */
	public List<Updatable> getUpdatables() {
		ArrayList<Updatable> tiles = new ArrayList<>();
		for(ImageTile i: tileList) {
			if(i instanceof Updatable) {
				tiles.add((Updatable)i);
			}
		}
		return tiles;
	}
	
	/**
	 * Adiciona uma ImageTile à tileList e à interface gráfica.
	 *
	 * @param image a ImageTile a ser adicionada
	 */
	public void addImage(ImageTile image){
		tileList.add(image);
		gui.addImage(image);
	}

	/**
	 * Remove uma ImageTile da tileList e da interface gráfica.
	 *
	 * @param image a ImageTile a ser removida
	 */
	public void removeImage(ImageTile image) {
	    tileList.remove(image);
	    gui.removeImage(image);
	}

	/**
	 * Adiciona uma sala ao jogo com base em um arquivo de texto.
	 *
	 * @param file o arquivo de texto contendo a definição da sala
	 */
	public void addFloor(String file) {		

		try {
			Scanner sc = new Scanner(new File(file));
			int count = 0;
			while(sc.hasNextLine() && count <= 10) {
				String tipo = sc.nextLine();
				for (int x=0; x<GRID_WIDTH; x++) {
					if(count < GRID_HEIGHT-1) {
						char objeto = tipo.charAt(x);
						if(String.valueOf(objeto).equals("#")) {
							wall = new Wall(new Point2D(x,count));
							tileList.add(wall);
						} else {
							floor = new Floor(new Point2D(x,count));
							tileList.add(floor);
						}
					}
				}
				count++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado");
		}
	}
	
	/**
	 * Retorna a ImageTile presente em uma determinada posição com base em sua classe.
	 *
	 * @param position a posição da ImageTile
	 * @param type     a classe da ImageTile
	 * @return a ImageTile encontrada ou null se não houver uma correspondência
	 */
	public ImageTile getElementAtPosition(Point2D position, Class<?> type) {
		for(ImageTile object : tileList) {
			if(position.equals(object.getPosition()) && type.isInstance(object)) {
				return object;
			}
		}
		return null;
	}

	/**
	 * Verifica se há uma ImageTile de uma determinada classe em uma posição específica.
	 *
	 * @param position a posição da ImageTile
	 * @param type     a classe da ImageTile
	 * @return true se houver uma ImageTile da classe fornecida na posição especificada, caso contrário, false
	 */
	public boolean isElementAtPosition(Point2D position, Class<?> type) {
		for(ImageTile object : tileList) {
			if(object.getPosition().equals(position) && type.isInstance(object)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna a porta presente em uma determinada posição.
	 *
	 * @param position a posição da porta
	 * @return a porta encontrada ou null se não houver uma porta na posição especificada
	 */
	public Door getDoorAtPosition(Point2D position) {
		for(ImageTile i: tileList) {
			if(position.equals(i.getPosition())) {
				if(i instanceof Door) {	
					return (Door) i;
				}
			}
		}
		return null;
	}
	
	/**
	 * Retorna um objeto Pickable presente em uma determinada posição na lista de inventário.
	 *
	 * @param position a posição do objeto
	 * @return o objeto Pickable encontrado ou null se não houver um objeto na posição especificada
	 */
	public Pickable getCatchableObjAtPosition(Point2D position) {
		if(!inventoryList.isEmpty()) {

			for(ImageTile i: inventoryList) {
				if(position.equals(i.getPosition())) {
					if(i instanceof Pickable) {	
						return (Pickable) i;
					}
				}
			}
		} 
		return null;
	}
	
	/**
	 * Retorna uma chave presente em uma determinada posição na lista de inventário.
	 *
	 * @param position a posição da chave
	 * @return a chave encontrada ou null se não houver uma chave na posição especificada
	 */
	public Key getKeyAtPosition(Point2D position) {
		if(!inventoryList.isEmpty()) {
			for(ImageTile i: inventoryList) {
				if(position.equals(i.getPosition())) {
					if(i instanceof Key) {	
						return (Key) i;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Cria objetos do jogo com base em um arquivo de texto.
	 *
	 * @param file o arquivo de texto contendo a definição dos objetos
	 */
	public void createObjects(String file) {

		try {
			Scanner sc = new Scanner(new File(file));
			int count = 0;
			while(sc.hasNextLine()) {
				count++;
				sc.nextLine();
				if(count >= GRID_HEIGHT) {
					String[] split = sc.next().split(",");
					String type = split[0];
					int x = Integer.parseInt(split[1]);
					int y = Integer.parseInt(split[2]);

					switch (type) {
					case "Skeleton":
						skeleton = new Skeleton(new Point2D(x, y));
						tileList.add(skeleton);
						break;
					case "Bat":
						bat = new Bat(new Point2D(x, y));
						tileList.add(bat);
						break;
					case "Sword":
						sword = new Sword(new Point2D(x, y));
						tileList.add(sword);
						break;
					case "Key":
						key = new Key(new Point2D(x, y), split[3]);
						tileList.add(key);
						break;
					case "Door":
						String newRoom = split[3];
						int newX = Integer.parseInt(split[4]);
						int newY = Integer.parseInt(split[5]);
						if(split.length > 6) {
							String keyId = split[6];
							door = new Door(new Point2D(x, y), newRoom, newX, newY, keyId);
						} else {
							door = new Door(new Point2D(x, y), newRoom, newX, newY, "");
						}
						tileList.add(door);
						break;
					case "HealingPotion":
						healingPotion = new HealingPotion(new Point2D(x, y));
						tileList.add(healingPotion);
						break;
					case "Treasure":
						treasure = new Treasure(new Point2D(x, y));
						tileList.add(treasure);
						break;
					case "Thug":
						thug = new Thug(new Point2D(x, y));
						tileList.add(thug);
						break;
					case "Armor":
						armor = new Armor(new Point2D(x, y));
						tileList.add(armor);
						break;
					}
				}
			}
			sendImagesToGUI();
			gui.update();
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado");
		}
	}


}
