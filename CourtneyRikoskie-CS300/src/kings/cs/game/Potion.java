package kings.cs.game;

import java.util.HashSet;
import java.util.Iterator;
/**
 * Represents a potion.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public class Potion extends Item implements Makeable, Useable {
    /** The ingredients needed to make the potion. */
    private HashSet<Ingredient> ingredients;
    /** The container that the potion is in. */
    private PotionContainer potionContainer;
    /** Whether or not the potion has been made. */
    private boolean made;

    /**
     * Constructs a new Potion.
     * 
     * @param theName The name of the potion.
     * @param theDescription The description of the potion.
     * @param thePointValue The point value of the potion.
     * @param theWeight The weight(in ounces) of the potion.
     */
    public Potion(String theName, String theDescription, int thePointValue, double theWeight) {
        super(theName, theDescription, thePointValue, theWeight);
        ingredients = new HashSet<Ingredient>();
        potionContainer = null;
        made = false;
    }

    /**
     * Adds an ingredient needed to make the potion.
     * 
     * @param ingredient An ingredient needed to make the potion.
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * Gets whether or not the potion was made.
     * 
     * @return Whether or not the potion was made.
     */
    public boolean wasMade() {
        return made;
    }
    
    /**
     * Sets the potion's container.
     * 
     * @param container The potion's container.
     */
    public void setContainer(PotionContainer container) {
        potionContainer = container;
    }

    /**
     * Gets the container that the potion is in.
     * 
     * @return The container that the potion is in.
     */ 
    public PotionContainer getContainer() {
        return potionContainer;
    }

    /**
     * Makes a potion, if all needed ingredients are available.
     * 
     * @param player The player character.
     * @param room The current room.
     * @param world The world.
     * @param container A container that ingredients may be found in.
     * @param cauldron The container which holds newly made potions.
     * @return The ingredients used to make the potion.
     */
    public String makePotion(Player player, Room room, World world, Container container, PotionContainer cauldron) {
        boolean found = false;
        boolean allFound = true;
        String result = null;

        Iterator<Ingredient> firstIter = ingredients.iterator();

        while (firstIter.hasNext() && allFound == true) {
            String ingredientName = firstIter.next().getName();
            found = false;

            if (player.isInInventory(ingredientName) || room.isInRoom(ingredientName)) {
                found = true;
            }
            else if (container != null) {
                if (container.isInContainer(ingredientName)) {
                    found = true;
                }
            }
            if (found == false) {
                allFound = false;
            }

        }

        if (allFound == true) {
            result = "You made a " + super.getName() + ".\n\n";
            result += "To do so, you used:\n";

            Iterator<Ingredient> secondIter = ingredients.iterator();

            while (secondIter.hasNext()) {
                Ingredient current = secondIter.next();
                Item ingredient = null;
                String ingredientName = current.getName();

                result += "      " + ingredientName;

                if (player.isInInventory(ingredientName)) {
                    ingredient = player.getItem(ingredientName);
                }
                else if (room.isInRoom(ingredientName)) {
                    ingredient = room.getItem(ingredientName);
                }
                else {
                    if (container != null) {
                        if (container.isInContainer(ingredientName)) {
                            ingredient = container.getItem(ingredientName);
                        }
                    }
                }

                Ingredient anIngredient = (Ingredient)ingredient;
                anIngredient.setNumberInGroup(anIngredient.getNumberInGroup() - 1);

                int numberInGroup = anIngredient.getNumberInGroup();

                if (numberInGroup == 0) {
                    if (player.isInInventory(ingredientName)) {
                        player.removeItem(ingredientName);
                    }
                    else if (room.isInRoom(ingredientName)) {
                        room.removeItem(ingredientName);
                    }
                    else if (container.isInContainer(ingredientName)) {
                        container.removeItem(ingredientName);
                    }
                }
            }

            potionContainer = cauldron;
            cauldron.addPotion(this);
            made = true;
        }
        else {
            result = "You don't have the necessary ingredients.";
        }

        return result;
    }

    /**
     * Gets a description of the ingredients needed to make the potion.
     * 
     * @return A description of the ingredients needed to make the potion.
     */
    public String toString() {
        String result = "Ingredients needed to make a " + getName() + ":\n";

        Iterator<Ingredient> iter = ingredients.iterator();

        while (iter.hasNext()) {
            Item current = iter.next();
            String itemName = current.getName();

            result += "      " + itemName + "\n";
        }

        return result;
    }

    /**
     * Uses a potion on specified item.
     * 
     * @param player The player character
     * @param room The room that the player character is currently in.
     * @param theItem The specified item.
     * @param world The world.
     * @return Whether the potion was successfully used on the item.
     */
    public String use(Player player, Room room, Item theItem, World world) {
        String result = "You used the " + getName() + ".";
        String itemName = theItem.getName();
        Container theContainer = null;

        if (room.isInRoomContainer(getName())) {
            theContainer = room.getContainer(getName());
        }
        if (player.isInInventoryContainer(getName())) {
            theContainer = player.getContainer(getName());
        }
        
        PotionContainer container = (PotionContainer)theContainer;
        
        switch (getName()) {
            case "shrinking potion":
                if (theItem instanceof Ingredient) {
                    result = "You probably shouldn't use a shrinking potion on that.  It will mess up the porportion.";
                }
                else if (itemName.equals("cauldron")) {
                    result = "You don't want to lessen the amount of potion you are able to make.";
                }
                else if (itemName.equals("vial") || itemName.equals("phial")) {
                    result = "You don't want to lessen the amount of potion you are able to carry around.";
                }
                else if (itemName.equals("jewelry box") || itemName.equals("cellar key") || itemName.equals("book on warding and barriers")) {
                    result = "You don't want to mess around with Master's things.";
                }
                else if (itemName.equals("shed") || itemName.equals("cat")) {
                    result = "It probably wouldn't be the smartest thing in the world to use a shrinking potion on that.";  
                }
                else if (itemName.equals("herb pouch")) {
                    result = "Being able to carry less herbs is a bad thing.";
                }
                else {
                    double weight = theItem.getWeight();
                    theItem.setWeight(weight / 4);
                    theItem.setActive(1);
                    container.empty();
                    potionContainer = null;
                }
                break;
            case "duplication potion":                
                if (!(itemName.equals("shed") || itemName.equals("cat") || itemName.contains("duplicate") || theItem instanceof Ingredient)) {
                    Item item = new Item("duplicate " + theItem.getName(), theItem.getDescription(), 0, theItem.getWeight());
                    result += "\n\nA new " + itemName + " appeared in front of you.";
                    room.addItem(item);
                    
                    if (itemName.equals("broadsword")) {
                        Character syl = world.getCharacter("syl");
                        syl.addTradeItem(item);
                    }

                    container.empty();
                    potionContainer = null;
                }
                else {
                    result = "You probably shouldn't use a duplication potion on that.  It's asking for trouble.";
                }
                break;
            case "scent remover":
                theItem.setActive(2);
                break;
            case "unknown potion":
                result = "No.";
                break;
            case "revealing potion":
                if (!(itemName.equals("barrier rune"))) {
                    result += "\n\nIt doesn't seem to do anything.";
                    container.empty();
                    potionContainer = null;
                }
                else {
                    Room clearing = world.getRoom("Clearing");
                    clearing.removeItem("hidden barrier rune");
                    
                    result += "\n\nIn a quick flash, another rune appears next to the old one.  It burns up into nothing.";
                    container.empty();
                    potionContainer = null;
                }
                break;
        }

        return result;
    }
}
