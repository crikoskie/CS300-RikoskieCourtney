package kings.cs.game;

/**
 * Represents an herb container.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public class HerbContainer extends Container {
    /** The max number of herbs able to be carried. */
    public static final int MAX_HERBS;
    /** Keeps track of the number of herbs in container. */
    private int herbCounter;
    
    /**
     * Static initializer.
     */
    static {
        MAX_HERBS = 15;
    }
    
    /**
     * Constructs a new HerbContainer.
     * 
     * @param theName The name of the herb container.
     * @param theDescription The description of the herb container.
     * @param thePointValue The point value of the herb container.
     * @param theWeight The weight(in ounces) of the herb container.
     */
    public HerbContainer(String theName, String theDescription, int thePointValue, double theWeight) {
        super(theName, theDescription, thePointValue, theWeight);
    }
    
    /**
     * Adds an ingredient to the herb container.
     * 
     * @param theItem The ingredient to be added.
     */
    public void addItem(Item theItem) {        
        if (theItem instanceof Ingredient) {
            super.addItem(theItem);
        }
    }
}