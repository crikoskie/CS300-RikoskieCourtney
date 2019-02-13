package kings.cs.game;

import java.util.HashSet;
import java.util.Iterator;
/**
 * Represents a non-player character.
 * 
 * @author Courtney Rikoskie.
 * @version Spring 2017
 */
public class Character {
    /** The name of the non-player character. */
    private String name;
    /** The responses that the non-player character is able to give. */
    private Conversation responses;
    /** The inventory of the non-player character. */
    private Item inventory;
    /** The items that the non-player character will trade for. */
    private HashSet<Item> tradeItems;
    /** The message that will be displayed after a trade has occurred. */
    private String tradeMessage;
    
    /**
     * Constructs a new Character.
     * 
     * @param theName The name of the non-player character.
     * @param theResponses The responses that the non-player character is able to give.
     */
    public Character(String theName, Conversation theResponses) {
        name = theName;
        responses = theResponses;
        inventory = null;
        tradeItems = new HashSet<Item>();
        tradeMessage = null;
    }
    
    /**
     * Gets the non-player character's name.
     * 
     * @return The non-player character's name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the non-player character's responses.
     * 
     * @return The non-player character's responses.
     */
    public Conversation getResponses() {
        return responses;
    }
    
    /**
     * Sets the non-player character's responses.
     * 
     * @param theResponses The new responses.
     */
    public void setResponses(Conversation theResponses) {
        responses = theResponses;
    }
    
    /**
     * Gets the non-player character's inventory.
     *
     * @return The non-player character's inventory.
     */
    public Item getInventory() {
        return inventory;
    }
    
    /**
     * Sets the non-player character's inventory.
     * 
     * @param theItem The new inventory item.
     */
    public void setInventory(Item theItem) {
        inventory = theItem;
    }
    
    /**
     * Gets whether or not the specified item is one that the non-player character will trade for.
     * 
     * @param theName The name of the specified item.
     * @return Whether or not the specified item is one that the non-player character will trade for.
     */
    public boolean isTradeItem(String theName) {
        boolean found = false;
        Iterator<Item> iter = tradeItems.iterator();
        
        while (iter.hasNext() && !found) {
            Item current = iter.next();
            String itemName = current.getName();
            
            if (theName.equals(itemName)) {
                found = true;
            }
        }
        
        return found;
    }
    
    /**
     * Adds an item that the non-player character will trade for.
     * 
     * @param theItem The new item that the non-player character will trade for.
     */
    public void addTradeItem(Item theItem) {
        tradeItems.add(theItem);
    }
    
    /**
     * Gets the trade message.
     * 
     * @return The trade message.
     */
    public String getTradeMessage() {
        return tradeMessage;
    }
    
    /**
     * Sets the trade message.
     * 
     * @param theMessage The new trade message.
     */
    public void setTradeMessage(String theMessage) {
        tradeMessage = theMessage;
    }
    
    /**
     * Gets the specified trade item.
     * 
     * @param theName The name of the trade item.
     * @return The specified trade item.
     */
    public Item getTradeItem(String theName) {
        Iterator<Item> iter = tradeItems.iterator();
        Item item = null;
        boolean found = false;
        
        while (!found && iter.hasNext()) {
            Item current = iter.next();
            String currentName = current.getName();
            
            if (theName.equals(currentName)) {
                item = current;
            }
        }
        
        return item;
    }
}
