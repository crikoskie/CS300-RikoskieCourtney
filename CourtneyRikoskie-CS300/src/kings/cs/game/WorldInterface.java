package kings.cs.game;

import java.util.HashSet;

public interface WorldInterface {
	 public Room getRoom(String name);
	 public Character getCharacter(String theName);
	public HashSet<Item> getTradeItems();
	public Item getPotion(String itemName);
}
