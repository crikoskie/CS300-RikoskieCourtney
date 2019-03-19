package kings.cs.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game game;
	private World world;
	private Player player;

	@Before
	public void setup() {
		WriterInterface mock = new MockWriter();
		game = new Game(mock);
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
		rest.add("south");
		Command command = new Command(firstWord, rest);
		game.processCommand(command);
		Room clearing = world.getRoom("Clearing");
		clearing.removeItem("hidden barrier rune");
		boolean loss = game.getWantToContinue();

		assertTrue("The game should continue certain conditions are met before moving down from starting position.",
				loss);
	}
}