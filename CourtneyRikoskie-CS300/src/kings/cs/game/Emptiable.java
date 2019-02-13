package kings.cs.game;

/**
 * Allows the user to empty and add potions to potion containers.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public interface Emptiable {
    /**
     * Empties a specific container.
     * 
     * @return Whether emptying the item was successful.
     */
    public String empty();
    
    /**
     * Returns whether or not the potion container is empty.
     * 
     * @return Whether or not the potion container is empty.
     */
    public boolean isEmpty();
    
    /**
     * Adds an item to the potion container.
     * 
     * @param theItem The name of the item to be added.
     * @return Whether adding the item was successful.
     */
    public String addPotion(Item theItem);
}
