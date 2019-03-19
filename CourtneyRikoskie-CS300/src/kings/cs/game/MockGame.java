package kings.cs.game;

import java.util.HashSet;
import java.util.Iterator;

public class MockGame extends Game {
	protected WorldInterface makeWorld() {
		return new MockWorld();
	}
	
	protected WriterInterface makeWriter() {
		return new MockWriter();
	}
	
	protected ReaderInterface makeReader() {
		return new MockReader();
	}
	
	protected Player makePlayer() {
		Player player = new Player(getWorld().getRoom("front porch"));
		
		HashSet<Item> items = getWorld().getTradeItems();
		Iterator<Item> iter = items.iterator();
		
		Item theItem = iter.next();
		
		player.addToInventory(theItem);
		
		return player;
	}
}
