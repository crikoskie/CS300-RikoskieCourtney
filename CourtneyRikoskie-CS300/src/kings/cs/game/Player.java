package kings.cs.game;

import java.util.HashSet;
import java.util.Iterator;
/**
 * Represents the player character in the game.
 * 
 * @author Courtney Rikoskie 
 * @version 2017.01.24
 */
public class Player {
    /** The maximum amount of weight that the player character can carry. */
    public static final double MAX_WEIGHT;
    
    /** The room that the player character is currently in. */
    private Room currentRoom;
    /** The room that the player character was previously in. */
    private Room previousRoom;
    /** The items that the player character currently holds. */
    private HashSet<Item> inventory;
    
    static {
        MAX_WEIGHT = 128;
    }
    
    /**
     * Constructs a new Player.
     * 
     * @param startingRoom The room that the player character starts in.
     */
    public Player(Room startingRoom) {
        currentRoom = startingRoom;
        previousRoom = null;
        inventory = new HashSet<Item>();
    }
    
    /**
     * Gets the room that the player character is currently in. 
     * 
     * @return The room that the player character is currently in.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    /**
     * Gets the room that the player character was previously in.
     * 
     * @return The room that the player character was previously in.
     */
    public Room getPreviousRoom() {
        return previousRoom;
    }
    
    /**
     * Sets the room that the player character is currently in.  The previous room becomes
     * the one that the player was in before the current room.
     * 
     * @param theCurrentRoom The room that the player character has moved into.
     */
    public void setCurrentRoom(Room theCurrentRoom) {
        previousRoom = currentRoom;
        currentRoom = theCurrentRoom;
    }
    
    /**
     * Gets a description of the player character's inventory.
     * 
     * @return A description of the player character's inventory.
     */
    public String toString() {
        String result = "";
        
        if (inventory.size() == 0) {
            result = "You are not carrying anything.";
        }
        else {
            result = "You are carrying:" + "\n";
        }
        
        for (Item current : inventory) {
            String itemName = current.getName();
            
            if (current instanceof Ingredient) {
                Ingredient ingredient = (Ingredient)current;
                
                result += "       " + ingredient.getNumberInGroup() + " " + itemName + "\n";
            }
            else {
                result += "       " + itemName + "\n";
            }
        }
        
        return result;
    }
    
    /**
     * Adds the specified item to the inventory.
     * 
     * @param theItem The specified item to be added.
     * @return Whether or not the item was successfully added to the inventory.
     */
    public boolean addToInventory(Item theItem) {
        boolean added = false;
        
        if (getTotalWeight() + theItem.getWeight() < MAX_WEIGHT) {
            if (theItem instanceof Ingredient) {
                Ingredient ingredient = (Ingredient)theItem;
                String itemName = ingredient.getName();
                boolean found = false;
                
                for (Item current : inventory) { 
                    String currentName = current.getName();
                    
                    if (itemName.equals(currentName)) {
                        found = true;
                        
                        Ingredient anIngredient = (Ingredient)current;
                        
                        anIngredient.setNumberInGroup(anIngredient.getNumberInGroup() + 1);
                        ingredient.setNumberInGroup(ingredient.getNumberInGroup() - 1);
                    }
                }
                
                if (!found) {
                    theItem = ingredient.split(1);
                    inventory.add(theItem);
                }
            }
            else {
                inventory.add(theItem);
            }
            added = true;
        }
        
        return added;
    }
    
    /**
     * Gets the specified item from the player character's inventory.
     * 
     * @param theName The name of the item.
     * @return The specified item from the player character's inventory.
     */
    public Item getItem(String theName) {
        Item item = null;
        boolean found = false;
        Iterator<Item> iter = inventory.iterator();
        
        while (!found && iter.hasNext()) {
            Item current = iter.next();
            String itemName = current.getName();
            
            if (itemName.equals(theName)) {
                item = current;
                found = true;
            }
        }
        
        return item;
    }
    
    /**
     * Removes the specified item from the player character's inventory.
     * 
     * @param theName The name of the item.
     * @return The specified item from the player character's inventory.
     */
    public Item removeItem(String theName) {
        Item item = null;
        boolean found = false;
        Iterator<Item> iter = inventory.iterator();
        
        while (!found && iter.hasNext()) {
            Item current = iter.next();
            String itemName = current.getName();
            
            if (itemName.equals(theName)) {
                item = current;
                found = true;
                iter.remove();
            }
        }
        
        return item;
    }
    
    /**
     * Gets whether or not the specified item is in the player character's inventory.
     * 
     * @param theName The name of the specified item.
     * @return Whether or not the specified item is in the player character's inventory.
     */
    public boolean isInInventory(String theName) {
        Iterator<Item> iter = inventory.iterator();
        boolean found = false;
        
        while (!found && iter.hasNext()) {
            Item current = iter.next();
            String itemName = current.getName();
            
            if (itemName.equals(theName)) {
                found = true;
            }
        }
        
        return found;
    }
    
    /**
     * Gets whether or not the specified item can be found in a container within the player
     *  character's inventory.
     * 
     * @param theName The name of the specified item.
     * @return Whether or not the specified item can be found in a container within the player
     *  character's inventory.
     */
    public boolean isInInventoryContainer(String theName) {
        Iterator<Item> iter = inventory.iterator();
        boolean found = false;
        
        while (!found && iter.hasNext()) {
            Item current = iter.next();
            
            if (current instanceof Container) {
                Container container = (Container)current;
                
                if (container.isInContainer(theName)) {
                    found = true;
                }
            }
        }
        
        return found;
    }
    
    /**
     * Gets the container that the specified item is found in.
     * 
     * @param theName The name of the specfied item.
     * @return The container that the specified item is found in.
     */
    public Container getContainer(String theName) {
        Iterator<Item> iter = inventory.iterator();
        boolean found = false;
        Container container = null;
        
        while (!found && iter.hasNext()) {
            Item current = iter.next();
            
            if (current instanceof Container) {
                container = (Container)current;
                
                if (container.isInContainer(theName)) {
                    found = true;
                }
            }
        }
        
        return container;
    }
    
    /**
     * Gets the total weight (in ounces) that the player character is carrying.
     * 
     * @return The total weight (in ounces) that the player character is carrying.
     */
    public double getTotalWeight() {
        double totalWeight = 0;
        
        for (Item current : inventory) {
            totalWeight += current.getWeight();
        }
        
        return totalWeight;
    }
}
