package kings.cs.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game game;
	private WorldInterface world;
	private Player player;

	@Before
	public void setup() {
		game = new MockGame();
		world = game.getWorld();
		player = game.getPlayer();
	}

	@Test
	public void testMoveNorthFromStart() {
		CommandEnum firstWord = CommandWords.getCommand("go");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("north");
		Command command = new Command(firstWord, rest);
		game.processCommand(command);

		assertSame(
				"The player does not reach the correct room after traveling north from the front porch (starting position).",
				player.getCurrentRoom(), world.getRoom("Living Room"));
	}

	@Test
	public void testMoveEastFromStart() {
		CommandEnum firstWord = CommandWords.getCommand("go");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("east");
		Command command = new Command(firstWord, rest);
		game.processCommand(command);

		assertSame("The player should not move if trying to go east from the front porch (starting position).",
				player.getCurrentRoom(), world.getRoom("Front Porch"));
	}

	@Test
	public void testMoveWestFromStart() {
		CommandEnum firstWord = CommandWords.getCommand("go");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("west");
		Command command = new Command(firstWord, rest);
		game.processCommand(command);

		assertSame("The player should not move if trying to go west from the front porch (starting position).",
				player.getCurrentRoom(), world.getRoom("Front Porch"));
	}

	@Test
	public void testMoveDownFromStartUnclearedCondition() {
		CommandEnum firstWord = CommandWords.getCommand("go");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("down");
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		boolean loss = game.getWantToContinue();

		assertFalse(
				"The player should lose the game if certain conditions are not met before moving down from starting position.",
				loss);
	}

	@Test
	public void testMoveDownFromStartClearedCondition() {
		CommandEnum firstWord = CommandWords.getCommand("go");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("down");
		Command command = new Command(firstWord, rest);
		
		Room clearing = world.getRoom("Clearing");
		clearing.removeItem("hidden barrier rune");
		
		game.processCommand(command);
		boolean loss = game.getWantToContinue();

		assertTrue("The game should continue if certain conditions are met before moving down from starting position.",
				loss);
		assertEquals("The player should move.", world.getRoom("North Path"), player.getCurrentRoom());
	}
	
	@Test
	public void testTradeIncorrectItem() {
		Item item = new Item("Item", "An item", 10, 10);
		player.addToInventory(item);
		
		CommandEnum firstWord = CommandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		MockReader reader = (MockReader) game.getReader();
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		MockWriter writer = (MockWriter) game.getWriter();
		String current = writer.getCurrentString();
		
		assertEquals("They don't want to trade for that.", current);
	}
	
	@Test
	public void testTradeCorrectItem() {		
		CommandEnum firstWord = CommandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Trade");
		
		MockReader reader = (MockReader) game.getReader();
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		MockWriter writer = (MockWriter) game.getWriter();
		String current = writer.getCurrentString();
		
		assertEquals("Person: Thanks", current);
	}
	
	@Test
	public void testTradeNoItem() {		
		CommandEnum firstWord = CommandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		MockReader reader = (MockReader) game.getReader();
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		MockWriter writer = (MockWriter) game.getWriter();
		String current = writer.getCurrentString();
		
		assertEquals("You search your pockets, but there is no such item to be found.", current);
	}
	
	@Test
	public void testTradeTooHeavy() {	
		Item item = new Item("An Item", "An item", 5, 115);
		player.addToInventory(item);
		
		CommandEnum firstWord = CommandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Trade");
		
		MockReader reader = (MockReader) game.getReader();
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		MockWriter writer = (MockWriter) game.getWriter();
		String current = writer.getCurrentString();
		
		assertEquals("You can't carry their item along with what you already have.", current);
	}
}