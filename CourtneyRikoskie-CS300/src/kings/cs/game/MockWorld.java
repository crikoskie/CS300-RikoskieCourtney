package kings.cs.game;

import java.util.HashMap;
import java.util.HashSet;

public class MockWorld implements WorldInterface {    
	private HashMap<String, Room> rooms;
    private HashSet<Item> tradeItems;
    private HashMap<String, Character> characters;
	
	public MockWorld() {
		rooms = new HashMap<String, Room>();
		tradeItems = new HashSet<Item>();
		characters = new HashMap<String, Character>();
		
		Room frontPorch = new Room("Front Porch", "The porch is small, only about half the length of the actual house, and the worn wood creaks beneath your feet.  There are no furnishings besides an inelegant wooden chair.  Behind it, a window peers into the living room, but green curtains block the view.  A sign hangs on the door."); 
        Room livingRoom = new Room("Living Room", "The lighting is dim, the curtains over the window making it difficult to see.  Since it's the warm season, the fireplace is unlit.  Before it, two chairs sit on either side of a low table.  This is where Master does business with her customers and relaxes when she is off hours, but you rarely spend your time here.  It feels stuffy to you.");
        Room northPath = new Room("North Path", "Gravel crunching beneath your feet, you step onto a trodden down path.  The trees in this forest are known for their height, and you feel even smaller than usual.");
        Room clearing = new Room("Clearing", "For some reason, no trees have grown in this area.  You rest for a moment, a nearby boulder making a good seat, and listen to the grass rustle in the light breeze.");
        
        Item hiddenRune = new Item("hidden barrier rune", "An ancient symbol is written upon the paper in ink.  It looks a bit like the silhouette of a frog.", 0, 0);
        clearing.addItem(hiddenRune);
        
		addRoom(frontPorch);
        addRoom(livingRoom);
		addRoom(northPath);
		addRoom(clearing);
		
		createDoor(frontPorch, "north", livingRoom);
        createDoor(livingRoom, "south", frontPorch);
        
        createDoor(frontPorch, "down", northPath);
        createDoor(northPath, "up", frontPorch);
        
		Conversation responses = new Conversation("Person", "Bye");
		responses.addReply("hi", "Hi");
		responses.addReply("hia", "Yes");
		responses.addReply("hib", "No");
		responses.addReply("hiaa", "Bye");
		responses.addReply("hiba", "Bye");
		
		Character character = new Character("Person", responses);
		character.setTradeMessage("Thanks");
		characters.put("Person", character);
		
		frontPorch.addCharacter(character);
		
		Item anItem = new Item("Item", "An item", 10, 5);
		character.setInventory(anItem);
		
		Item trade = new Item("Trade", "To trade", 5, 10);
		character.addTradeItem(trade);
		
		tradeItems.add(trade);
	}
	
    private void addRoom(Room theRoom) {
        rooms.put(theRoom.getName().toLowerCase(), theRoom);
    }

    private void createDoor(Room from, String direction, Room to) {
        Door door = new Door(to);
        from.setExit(direction, door);
    }

	@Override
	public Room getRoom(String name) {
		return rooms.get(name.toLowerCase());
	}

	@Override
	public Character getCharacter(String theName) {
		return characters.get(theName);
	}

	@Override
	public HashSet<Item> getTradeItems() {
		return tradeItems;
	}

	@Override
	public Item getPotion(String itemName) {
		return null;
	}
}
