package kings.cs.game;

import java.util.HashMap;

public class MockWorld extends World {    
	public MockWorld() {
		Room start = new Room("Starting Room", "The start.");
		getRooms().put("Starting Room", start);
		
		Conversation responses = new Conversation("Person", "Bye");
		responses.addReply("hia", "Yes");
		responses.addReply("hib", "No");
		
		Character character = new Character("Person", responses);
		getCharacters().put("Person", character);
		
		start.addCharacter(character);
		
		Item anItem = new Item("Item", "An item", 10, 5);
		character.setInventory(anItem);
		
		Item trade = new Item("Trade", "To trade", 5, 10);
		character.addTradeItem(trade);
	}
}
