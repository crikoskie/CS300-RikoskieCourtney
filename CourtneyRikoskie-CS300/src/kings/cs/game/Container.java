package kings.cs.game;

import java.util.HashSet;
import java.util.Iterator;
/**
 * Represents a container which can hold other items.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public class Container extends Item {
    /** The items that the container holds. */
    private HashSet<Item> itemsContained;

    /**
     * Constructs a new Container.
     * 
     * @param theName The name of the container.
     * @param theDescription The description of the container.
     * @param thePointValue The point value of the container.
     * @param theWeight The weight (in ounces) of the container.
     */
    public Container(String theName, String theDescription, int thePointValue, double theWeight) {
        super(theName, theDescription, thePointValue, theWeight);
        itemsContained = new HashSet<Item>();
    }

    /**
     * Adds the specified item to the container.
     * 
     * @param theItem The item to be added.
     */
    public void addItem(Item theItem) {
        if (theItem instanceof Ingredient) {
            Ingredient ingredient = (Ingredient)theItem;
            String itemName = ingredient.getName();
            boolean found = false;

            for (Item current : itemsContained) { 
                String currentName = current.getName();

                if (itemName.equals(currentName)) {
                    found = true;

                    Ingredient anIngredient = (Ingredient)current;

                    anIngredient.setNumberInGroup(anIngredient.getNumberInGroup() + ingredient.getNumberInGroup());
                }
            }
            
            if (!found) {
                itemsContained.add(theItem);
            }
        }
        else {
            itemsContained.add(theItem);
        }
    }

    /**
     * Removes the specified item from the container.
     * 
     * @param theName The name of the item to be removed,
     * @return The item that was removed, if it exists.
     */
    public Item removeItem(String theName) {
        Item item = null;
        boolean found = false;
        Iterator<Item> iter = itemsContained.iterator();

        while (iter.hasNext() && found == false) {
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
     * Gets whether or not the container holds the specified item.
     * 
     * @param theName The name of the specified item.
     * @return Whether or not the container holds the specified item.
     */
    public boolean isInContainer(String theName) {
        boolean found = false;
        Iterator<Item> iter = itemsContained.iterator();

        while (iter.hasNext() && found == false) {
            Item current = iter.next();
            String itemName = current.getName();

            if (itemName.equals(theName)) {
                found = true;
            }
        }

        return found;
    }

    /**
     * Gets a complete description of the container.
     * 
     * @return A complete description of the container.
     */
    public String toString() {
        String result = super.toString() + "\n";

        if (!(itemsContained.size() == 0)) {
            result += "Contains:" + "\n";
        }

        for (Item current : itemsContained) {
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
     * Gets the specified item from the container.
     * 
     * @param theName The name of the item.
     * @return The specified item from the container.
     */
    public Item getItem(String theName) {
        Item item = null;
        boolean found = false;
        Iterator<Item> iter = itemsContained.iterator();

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
     * Gets the weight (in ounces) of the container.
     * 
     * @return The weight (in ounces) of the container.
     */
    public double getWeight() {
        double totalWeight = super.getWeight();

        for (Item current : itemsContained) {
            double itemWeight = current.getWeight();
            totalWeight += itemWeight;
        }

        return totalWeight;
    }
}
