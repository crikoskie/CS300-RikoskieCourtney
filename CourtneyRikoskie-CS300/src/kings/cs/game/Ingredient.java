package kings.cs.game;

/**
 * Represents an ingredient for a potion.
 * 
 * @author Courtney Rikoskie 
 * @version Spring 2017
 */
public class Ingredient extends Item {
    /** The number of the ingredient that are in this group of them. */
    private int numberInGroup;
    
    /**
     * Constructs a new Ingredient.
     * 
     * @param theName The name of the ingredient.
     * @param theDescription The description of the ingredient.
     * @param thePointValue The point value of the ingredient.
     * @param theWeight The weight (in ounces) of the ingredient.
     * @param theNumber The number in the group.
     */
    public Ingredient(String theName, String theDescription, int thePointValue, double theWeight, int theNumber) {
        super(theName, theDescription, thePointValue, theWeight);
        numberInGroup = theNumber;
    }
    
    /**
     * Gets the number of the ingredient that are in this group of them.
     * 
     * @return The number of th ingredient that are in this group of them.
     */
    public int getNumberInGroup() {
        return numberInGroup;
    }
    
    /**
     * Sets the number of the ingredient that are in this group of them.
     * 
     * @param theNumber The number of ingredients that are in this group of them.
     */
    public void setNumberInGroup(int theNumber) {
        numberInGroup = theNumber;
    }
    
    /**
     * Gets the weight of the ingredient.
     * 
     * @return The weight of the ingredient.
     */
    public double getWeight() {
        return super.getWeight() * numberInGroup;
    }
    
    /**
     * Splits an ingredient into two groups.
     * 
     * @param amountToTake How many to move into the second group.
     * @return A new Ingredient containing one of the items that were in this.
     */
    public Ingredient split(int amountToTake) {
        numberInGroup -= amountToTake;
        
        return new Ingredient(getName(), getDescription(), getPointValue(), getWeight(), amountToTake);
    }
}
