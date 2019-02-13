package kings.cs.game;

/**
 * Represents a container that can hold potions.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public class PotionContainer extends Container implements Emptiable {
    /** The contents of potion container. **/
    private Potion potion;
    
    /**
     * Constructs a new potion container.
     * 
     * @param theName The name of the container.
     * @param theDescription The description of the container.
     * @param thePointValue The point value of the container.
     * @param theWeight The weight (in ounces) of the container.
     */
    public PotionContainer(String theName, String theDescription, int thePointValue, double theWeight) {
        super(theName, theDescription, thePointValue, theWeight);
        potion = null;
    }    
    
    /**
     * Empties a specific container.
     * 
     * @return Whether emptying the container was successful.
     */
    public String empty() {
        String result = "Emptied.";
        
        String potionName = potion.getName();
        removeItem(potionName);
        potion = null;
        
        return result;
    }
    
    /**
     * Returns whether or not the potion container is empty.
     * 
     * @return Whether or not the potion container is empty.
     */
    public boolean isEmpty() {
        boolean empty = false;
       
        if (potion == null) {
            empty = true;
        }
        
        return empty;
    }
    
    /**
     * Adds an item to the potion container.
     * 
     * @param theItem The name of the item to be added.
     * @return A String containing whether or not adding the item was successful.
     */
    public String addPotion(Item theItem) {
        String result = null;
        
        if (theItem instanceof Potion) {
            Potion thePotion = (Potion)theItem;
            
            potion = thePotion;
            super.addItem(potion);
            potion.setContainer(this);
            result = "Poured.";
        }
        
        return result;
    }
    
    /**
     * Gets the potion within the container.
     * 
     * @return The potion within the container.
     */
    public Potion getPotion() {
        return potion;
    }
}
