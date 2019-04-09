package kings.cs.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game game;
	private WorldInterface world;
	private Player player;
	private CommandWords commandWords;
	private MockReader reader;
	private MockWriter writer;
	
	@Before
	public void setup() {
		game = new MockGame();
		world = game.getWorld();
		player = game.getPlayer();
		commandWords = game.getCommandWords();
		reader = (MockReader) game.getReader();
		writer = (MockWriter) game.getWriter();
	}

	@Test
	public void testMoveNorthFromStart() {
		CommandEnum firstWord = commandWords.getCommand("go");
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
		CommandEnum firstWord = commandWords.getCommand("go");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("east");
		Command command = new Command(firstWord, rest);
		game.processCommand(command);

		assertSame("The player should not move if trying to go east from the front porch (starting position).",
				player.getCurrentRoom(), world.getRoom("Front Porch"));
	}

	@Test
	public void testMoveWestFromStart() {
		CommandEnum firstWord = commandWords.getCommand("go");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("west");
		Command command = new Command(firstWord, rest);
		game.processCommand(command);

		assertSame("The player should not move if trying to go west from the front porch (starting position).",
				player.getCurrentRoom(), world.getRoom("Front Porch"));
	}

	@Test
	public void testMoveDownFromStartUnclearedCondition() {
		CommandEnum firstWord = commandWords.getCommand("go");
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
		CommandEnum firstWord = commandWords.getCommand("go");
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
		
		CommandEnum firstWord = commandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("They don't want to trade for that.", current);
	}
	
	@Test
	public void testTradeCorrectItem() {		
		CommandEnum firstWord = commandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Trade");
		
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("Person: Thanks", current);
	}
	
	@Test
	public void testTradeNoItem() {		
		CommandEnum firstWord = commandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("You search your pockets, but there is no such item to be found.", current);
	}
	
	@Test
	public void testTradeTooHeavy() {	
		Item item = new Item("An Item", "An item", 5, Player.MAX_WEIGHT - 13);
		player.addToInventory(item);
		
		CommandEnum firstWord = commandWords.getCommand("trade");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Trade");
	
		reader.setReply("Person");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("You can't carry their item along with what you already have.", current);
	}
	
	@Test
	public void testTakeNoSpecifiedItem() {
		CommandEnum firstWord = commandWords.getCommand("take");
		
		Command command = new Command(firstWord, null);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("What would you like to take?", current);
	}
	
	@Test
	public void testTakeItemNotInRoom() {
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("You search the room, but there is no such item to be found.", current);
	}
	
	@Test
	public void testTakeItemTooManyItems() {
		Item item = new Item("An Item", "An item", 5, Player.MAX_WEIGHT - 15);
		player.addToInventory(item);
		
		Item inRoom = new Item("Room item", "room", 5, 10);
		player.getCurrentRoom().addItem(inRoom);
		
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Room item");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("You try to pick it up, but you're already carrying so much.", current);
	}
	
	@Test
	public void testTakeItemTooHeavy() {	
		Item inRoom = new Item("Room item", "room", 5, 130);
		player.getCurrentRoom().addItem(inRoom);
		
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Room item");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("This item is too heavy for you to pick up.", current);
	}
	
	@Test
	public void testTakeItemNotIngredient() {	
		Item item = new Item("Item", "An item.", 5, 10);
		player.getCurrentRoom().addItem(item);
		
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("Taken.", current);
		assertFalse(player.getCurrentRoom().isInRoom("Item"));
		assertTrue(player.isInInventory("Item"));
	}
	
	@Test
	public void testTakeIngredientLeftOver() {	
		Ingredient item = new Ingredient("Ingredient", "An ingredient.", 5, 10, 5);
		player.getCurrentRoom().addItem(item);
		
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("Taken.", current);
		assertTrue(player.getCurrentRoom().isInRoom("Ingredient"));
		assertTrue(item.getNumberInGroup() == 4);
		assertTrue(player.isInInventory("Ingredient"));
		
		Ingredient inInventory = (Ingredient) player.getItem("Ingredient");
		assertTrue(inInventory.getNumberInGroup() == 1);
	}
	
	@Test
	public void testTakeIngredientNoneLeftOver() {	
		Ingredient item = new Ingredient("Ingredient", "An ingredient.", 5, 10, 1);
		player.getCurrentRoom().addItem(item);
		
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("Taken.", current);
		assertFalse(player.getCurrentRoom().isInRoom("Ingredient"));
		assertTrue(player.isInInventory("Ingredient"));
		
		Ingredient inInventory = (Ingredient) player.getItem("Ingredient");
		assertTrue(inInventory.getNumberInGroup() == 1);
	}
	
	@Test
	public void testTakeIngredientBackyardLessThan3() {	
		player.setCurrentRoom(world.getRoom("Backyard"));
		
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertEquals("Taken.", current);
		assertTrue(player.getCurrentRoom().isInRoom("Ingredient"));
		
		Ingredient inRoom = (Ingredient) player.getCurrentRoom().getItem("Ingredient");
		assertTrue(inRoom.getNumberInGroup() == 2);
		
		assertTrue(player.isInInventory("Ingredient"));
		
		Ingredient inInventory = (Ingredient) player.getItem("Ingredient");
		assertTrue(inInventory.getNumberInGroup() == 1);
		
		assertEquals("Some of the herbs are starting to look bare.", player.getCurrentRoom().getDescription());
	}
	
	@Test
	public void testTakeIngredientBackyardNoneLeft() {	
		player.setCurrentRoom(world.getRoom("Backyard"));
		Ingredient ingredient = (Ingredient) player.getCurrentRoom().getItem("Ingredient");
		ingredient.setNumberInGroup(1);;
		
		CommandEnum firstWord = commandWords.getCommand("take");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		
		String current = writer.getCurrentString();
		
		assertFalse(player.getCurrentRoom().isInRoom("Ingredient"));		
		assertTrue(player.isInInventory("Ingredient"));
		Ingredient inInventory = (Ingredient) player.getItem("Ingredient");
		assertTrue(inInventory.getNumberInGroup() == 1);
		
		assertFalse(game.getWantToContinue());
		assertEquals("", current);
	}
	
	@Test
	public void testPackNoSpecifiedItem() {
		CommandEnum firstWord = commandWords.getCommand("pack");
		
		Command command = new Command(firstWord, null);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("What would you like to pack?", current);
	}
	
	@Test
	public void testPackItemNotFound() {
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You can't pack an item you don't have.", current);
	}
	
	@Test
	public void testPackItemTooHeavy() {
		Item item = new Item("Item", "An item.", 5, Player.MAX_WEIGHT + 1);
		player.getCurrentRoom().addItem(item);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("This item is too heavy for you to move.", current);
	}
	
	@Test
	public void testPackItemNoContainer() {
		Item item = new Item("Item", "An item.", 5, 10);
		player.getCurrentRoom().addItem(item);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You don't see that container anywhere.", current);
	}
	
	@Test
	public void testPackItemContainerNotValid() {
		Item item = new Item("Item", "An item.", 5, 10);
		player.getCurrentRoom().addItem(item);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Item");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You can't pack things in that.", current);
	}
	
	@Test
	public void testPackItemInHerbContainer() {
		Item item = new Item("Item", "An item.", 5, 10);
		player.getCurrentRoom().addItem(item);
		
		HerbContainer container = new HerbContainer("Container", "A container.", 5, 5);
		player.getCurrentRoom().addItem(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You probably shouldn't try to put that in there.", current);
	}
	
	@Test
	public void testPackIngredientInHerbContainer() {
		Ingredient ingredient = new Ingredient("Ingredient", "An ingredient.", 5, 10, 5);
		player.getCurrentRoom().addItem(ingredient);
		
		HerbContainer container = new HerbContainer("Container", "A container.", 5, 5);
		player.addToInventory(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("Packed.", current);
		assertNull(player.getItem("Ingredient"));
		assertTrue(container.isInContainer("Ingredient"));
		
		Ingredient inContainer = (Ingredient) container.getItem("Ingredient");
		assertEquals(inContainer.getNumberInGroup(), 5);
	}
	
	@Test
	public void testPackIngredientInHerbContainerTooManyItems() {
		Item item = new Item("Item", "An item.", 5, Player.MAX_WEIGHT - 20);
		player.addToInventory(item);
		
		Ingredient ingredient = new Ingredient("Ingredient", "An ingredient.", 5, 10, 5);
		player.getCurrentRoom().addItem(ingredient);
		
		HerbContainer container = new HerbContainer("Container", "A container.", 5, 5);
		player.addToInventory(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You try to pick it up, but you're already carrying so much.", current);
		assertTrue(player.getCurrentRoom().isInRoom("Ingredient"));
		assertEquals(ingredient.getNumberInGroup(), 5);
		assertFalse(container.isInContainer("Ingredient"));
	}
	
	@Test
	public void testPackIngredientInHerbContainerNotInInventoryBackyard() {
		player.setCurrentRoom(world.getRoom("Backyard"));
		
		Ingredient ingredient = new Ingredient("Ingredient", "An ingredient.", 5, 10, 5);
		player.getCurrentRoom().addItem(ingredient);
		
		HerbContainer container = new HerbContainer("Container", "A container.", 5, 5);
		player.getCurrentRoom().addItem(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You probably shouldn't rip out all the herbs at once.", current);
		assertTrue(player.getCurrentRoom().isInRoom("Ingredient"));
		assertEquals(ingredient.getNumberInGroup(), 5);
		assertFalse(container.isInContainer("Ingredient"));
	}
	
	@Test
	public void testPackIngredientInHerbContainerNotInInventory() {		
		Ingredient ingredient = new Ingredient("Ingredient", "An ingredient.", 5, 10, 5);
		player.getCurrentRoom().addItem(ingredient);
		
		HerbContainer container = new HerbContainer("Container", "A container.", 5, 5);
		player.getCurrentRoom().addItem(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("Packed.", current);
		// Should be false?
		assertTrue(player.getCurrentRoom().isInRoom("Ingredient"));
		assertTrue(container.isInContainer("Ingredient"));
		
		Ingredient inContainer = (Ingredient) container.getItem("Ingredient");
		assertTrue(inContainer.getNumberInGroup() == 5);
	}
	
	@Test
	public void testPackIngredientInContainer() {		
		Ingredient ingredient = new Ingredient("Ingredient", "An ingredient.", 5, 10, 5);
		player.getCurrentRoom().addItem(ingredient);
		
		Container container = new Container("Container", "A container.", 5, 5);
		player.getCurrentRoom().addItem(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Ingredient");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You probably shouldn't try to put that in there.", current);
		assertTrue(player.getCurrentRoom().isInRoom("Ingredient"));
		assertFalse(container.isInContainer("Ingredient"));
		assertTrue(ingredient.getNumberInGroup() == 5);
	}
	
	@Test
	public void testPackItemInContainer() {		
		Item item = new Item("Item", "An item.", 5, 10);
		player.getCurrentRoom().addItem(item);
		
		Container container = new Container("Container", "A container.", 5, 5);
		player.getCurrentRoom().addItem(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("Packed.", current);
		// Should be false?
		assertTrue(player.getCurrentRoom().isInRoom("Item"));
		assertTrue(container.isInContainer("Item"));
	}
	
	@Test
	public void testPackItemInContainerInInventory() {		
		Item ingredient = new Item("Item", "An item.", 5, 10);
		player.getCurrentRoom().addItem(ingredient);
		
		Container container = new Container("Container", "A container.", 5, 5);
		player.addToInventory(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("Packed.", current);
		assertFalse(player.getCurrentRoom().isInRoom("Item"));
		assertTrue(container.isInContainer("Item"));
	}
	
	@Test
	public void testPackItemInContainerInInventoryTooHeavy() {		
		Item item = new Item("Item", "An item.", 5, Player.MAX_WEIGHT);
		player.getCurrentRoom().addItem(item);
		
		Container container = new Container("Container", "A container.", 5, 5);
		player.addToInventory(container);
		
		CommandEnum firstWord = commandWords.getCommand("pack");
		ArrayList<String> rest = new ArrayList<String>();
		rest.add("Item");
		
		reader.setReply("Container");
		
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
	
		String current = writer.getCurrentString();
		
		assertEquals("You try to pick it up, but you're already carrying so much.", current);
		assertTrue(player.getCurrentRoom().isInRoom("Item"));
		assertFalse(container.isInContainer("Item"));
	}
}