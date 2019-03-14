package kings.cs.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class PotionTest {
	private Potion potion;
	private Player player;
	private Room room;
	private World world;
	private Container container;
	private PotionContainer potionContainer;
	private HashSet<Item> inventory;
	private Ingredient ingred;
	
	@Before
	public void setUp() {
		potion = new Potion("potion", "potion", 5, 5);
		ingred = new Ingredient("ingred", "an ingredient", 5, 5, 10);
		potion.addIngredient(ingred);
		
		potionContainer = new PotionContainer("cauldron", "a cauldron", 10, 10);
		container = new Container("container", "a container", 10, 10);
		room = new Room("Room", "A room");
		player = new Player(room);
		inventory = player.getInventory();
	}
	
	@Test
	public void testMakePotionWithoutIngredients() {
		assertEquals("The player should not be able to make a potion without any ingredients.", "You don't have the necessary ingredients.", potion.makePotion(player, room, world, container, potionContainer));
		assertEquals("No ingredients should have been used.", ingred.getNumberInGroup(), 10);
		assertTrue("Nothing should be in the potion container.", potionContainer.isEmpty());
	}

	@Test
	public void testMakePotionWithIngredientsInRoomLeftOver() {
		room.addItem(ingred);
		
		assertEquals("The player should be able to make a potion with the ingredients in the room.", "You made a potion.\n\nTo do so, you used:\n      ingred", potion.makePotion(player, room, world, container, potionContainer));
		assertEquals("The number of ingredients in the group should be one less.", ingred.getNumberInGroup(), 9);
		assertEquals("The potion should be in the potion container.", potionContainer.getPotion(), potion);
	}
	
	@Test
	public void testMakePotionWithNoIngredientsInRoomLeftOver() {
		room.addItem(ingred);
		ingred.setNumberInGroup(1);
		
		assertEquals("The player should be able to make a potion with the ingredients in the room.", "You made a potion.\n\nTo do so, you used:\n      ingred", potion.makePotion(player, room, world, container, potionContainer));
		assertEquals("The number of ingredients in the group should be one less.", ingred.getNumberInGroup(), 0);
		assertNull("The ingredient should be used up and no longer exist in the room.", room.getItem("ingred"));
		assertEquals("The potion should be in the potion container.", potionContainer.getPotion(), potion);
	}
	
	@Test
	public void testMakePotionWithIngredientsInInventoryLeftOver() {
		inventory.add(ingred);
		
		assertEquals("The player should be able to make a potion with the ingredients in the inventory.", "You made a potion.\n\nTo do so, you used:\n      ingred", potion.makePotion(player, room, world, container, potionContainer));
		assertEquals("The number of ingredients in the group should be one less.", ingred.getNumberInGroup(), 9);
		assertEquals("The potion should be in the potion container.", potionContainer.getPotion(), potion);
	}
	
	@Test
	public void testMakePotionWithNoIngredientsInInventoryLeftOver() {
		inventory.add(ingred);
		ingred.setNumberInGroup(1);
		
		assertEquals("The player should be able to make a potion with the ingredients in the inventory.", "You made a potion.\n\nTo do so, you used:\n      ingred", potion.makePotion(player, room, world, container, potionContainer));
		assertEquals("The number of ingredients in the group should be one less.", ingred.getNumberInGroup(), 0);
		assertTrue("The ingredient should be used up and no longer exist in the inventory.", inventory.isEmpty());
		assertEquals("The potion should be in the potion container.", potionContainer.getPotion(), potion);
	}
	
	@Test
	public void testMakePotionWithIngredientsInContainerLeftOver() {
		container.addItem(ingred);
		
		assertEquals("The player should be able to make a potion with the ingredients in the container.", "You made a potion.\n\nTo do so, you used:\n      ingred", potion.makePotion(player, room, world, container, potionContainer));
		assertEquals("The number of ingredients in the group should be one less.", ingred.getNumberInGroup(), 9);
		assertEquals("The potion should be in the potion container.", potionContainer.getPotion(), potion);
	}
	
	@Test
	public void testMakePotionWithNoIngredientsInContainerLeftOver() {
		container.addItem(ingred);
		ingred.setNumberInGroup(1);
		
		assertEquals("The player should be able to make a potion with the ingredients in the container.", "You made a potion.\n\nTo do so, you used:\n      ingred", potion.makePotion(player, room, world, container, potionContainer));
		assertEquals("The number of ingredients in the group should be one less.", ingred.getNumberInGroup(), 0);
		assertNull("The ingredient should be used up and no longer exist in the container.", container.getItem("ingred"));
		assertEquals("The potion should be in the potion container.", potionContainer.getPotion(), potion);
	}
}
