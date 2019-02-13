package kings.cs.game;

import java.util.ArrayList;
/**
 * Represents an item within the game.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public class Item
{
    /** The name of the item. */
    private String name;
    /** The description of the item. */
    private ArrayList<String> descriptions;
    /** The point value of the item. */
    private int pointValue;
    /** The weight (in ounces) of the item. */
    private double weight;
    /** The active element. */
    private int active;
    
    /**
     * Constructs a new Item.
     * 
     * @param theName The name of the item.
     * @param theDescription The description of the item.
     * @param thePointValue The point value of the item.
     * @param theWeight The weight (in ounces) of the item.
     */
    public Item(String theName, String theDescription, int thePointValue, double theWeight) {
        name = theName;
        descriptions = new ArrayList<String>();
        descriptions.add(theDescription);
        active = 0;
        pointValue = thePointValue;
        weight = theWeight;
    }

    /**
     * Gets the name of the item.
     * 
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the description of the item.
     * 
     * @return The description of the item.
     */
    public String getDescription() {
        return descriptions.get(active);
    }
    
    /**
     * Gets the point value of the item.
     * 
     * @return The point value of the item.
     */
    public int getPointValue() {
        int itemPoints = pointValue;
        setPointValue(0);
        
        return itemPoints;
    }
    
    /**
     * Sets the point value of the item.
     * 
     * @param thePoints The number of points that the player will receive upon added picking up the item.
     */
    public void setPointValue(int thePoints) {
        pointValue = thePoints;
    }
    
    /**
     * Gets the weight (in ounces) of the item.
     * 
     * @return The weight (in ounces) of the item.
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Sets the description of the item.
     * 
     * @param element The element.
     * @param theDescription The new description of the item.
     */
    public void setDescription(int element, String theDescription) {
        descriptions.set(element, theDescription);
    }
    
    /**
     * Sets the weight (in ounces) of the item.
     * 
     * @param theWeight The new weight (in ounces) of the item.
     */
    public void setWeight(double theWeight) {
        weight = theWeight;
    }
    
    /**
     * Gets a complete description of the item.
     * 
     * @return A complete description of the item.
     */
    public String toString() {
        String result = name + ": " + descriptions.get(active);
        
        return result;
    }
    
     /**
     * Adds a description.
     * 
     * @param theDescription The description to be added.
     */
    public void addDescription(String theDescription) {
        descriptions.add(theDescription);
    }
    
     /**
     * Sets the active element, changing the description that is displayed.
     * 
     * @param element The new element.
     */
    public void setActive(int element) {
        active = element;
    }
}
