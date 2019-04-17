package kings.cs.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Room room;
	private Player player;
	private Ingredient ingred;
	private Item item;
	private HashSet<Item> inventory;
	
	@Before
	public void setUp() {
		room = new Room("Room", "A room");
		ingred = new Ingredient("ingredient", "an ingredient" , 1, 2, 30);
		player = new Player(room);
		item = new Item("item", "an item", 1, 20);
		inventory = player.getInventory();
	}
	
	@Test
	public void testItemWeightTooHeavy() {
		Item item = new Item("item", "an item", 1, 130);
		boolean added = player.addToInventory(item);
		
		assertFalse(added);
		assertTrue(player.getInventory().isEmpty());
	}
	
	@Test
	public void testItemWeightAbleToCarry() {
		boolean added = player.addToInventory(item);
		
		HashSet<Item> inventory = player.getInventory();
		
		assertTrue(added);
		
		assertEquals("There should be one item in the inventory.", inventory.size(), 1);
		assertTrue("'item' should be in the inventory.", inventory.remove(item));
	}
	
	@Test
	public void testAddIngredientToInventoryOnce() {
		boolean added = player.addToInventory(ingred);
		
		assertTrue(added);
		
		assertEquals("There should be one item in the inventory.", inventory.size(), 1);
		assertEquals("'ingred' should now have 29 in the group.", ingred.getNumberInGroup(), 29);
		
		Iterator<Item> iter = inventory.iterator();
		
		Item current = iter.next();
		
		assertTrue("Item in inventory should be an ingredient.", current instanceof Ingredient);
		
		Ingredient invIngred = (Ingredient) current;
		
		assertEquals("Item in inventory should have one in group.", invIngred.getNumberInGroup(), 1);
	}
	
	@Test
	public void testAddSameIngredientToInventoryMultipleTimes() {
		player.addToInventory(ingred);		
		boolean added = player.addToInventory(ingred);
		
		assertTrue(added);
		
		assertEquals("There should be one item in the inventory.", inventory.size(), 1);
		assertEquals("'ingred' should now have 28 in the group.", ingred.getNumberInGroup(), 28);
		
		Iterator<Item> iter = inventory.iterator();
		
		Item current = iter.next();
		
		assertTrue("Item in inventory should be an ingredient.", current instanceof Ingredient);
		
		Ingredient invIngred = (Ingredient) current;
		
		assertEquals("Item in inventory should have two in the group.", invIngred.getNumberInGroup(), 2);
	}
}