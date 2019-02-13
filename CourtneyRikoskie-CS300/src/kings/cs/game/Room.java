package kings.cs.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 * 
 * This class is part of the "Campus of Kings" application. "Campus of Kings" is a
 * very simple, text based adventure game.
 * 
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via doors. The doors are labeled north, east, south, west.
 * For each direction, the room stores a reference to an instance of door.
 * 
 * @author Maria Jump
 * @version 2015.02.01
 */
public class Room {
    /** Counter for the total number of rooms created in the world. */
    private static int counter;
    /** The name of this room.  Room names should be unique. */
    private String name;
    /** The description of this room. */
    private ArrayList<String> descriptions;
    /** The number of points that the player will receive upon entering a room. */
    private int points;
    /** The active element. */
    private int active;
    /** The non-player characters found in the room. */
    private HashSet<Character> characters;

    /** The room's exits. */
    private HashMap<String, Door> exits;
    /** The room's items. */
    private HashSet<Item> items;

    /**
     * Static initializer.
     */
    static {
        counter = 0;
    }
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     * 
     * @param name  The room's name.
     * @param description
     *            The room's description.
     */
    public Room(String name, String description) {
        this.name = name;
        descriptions = new ArrayList<String>();
        descriptions.add(description);
        active = 0;
        characters = new HashSet<Character>();
        exits = new HashMap<String, Door>();
        points = 0;
        items = new HashSet<Item>();
        counter++;
    }

    /**
     * Returns the name of this room.
     * 
     * @return The name of this room.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this room.
     * 
     * @return The description of this room.
     */
    public String getDescription() {
        return descriptions.get(active);
    }

    /**
     * Returns the number of rooms that have been created in the world.
     * @return The number of rooms that have been created in the wo rld.
     */
    public static int getCounter() {
        return counter;
    }

    /**
     * Returns a string description including all the details of a Room.
     * For example,
     *          Outside:
     *          You are outside in the center of the King's College campus.
     *          Exits: north east south west
     *          
     * @return A string representing all the detail of a Room.
     */
    public String toString() {
        String roomDetails = "";

        roomDetails += (getName() + ":" + "\n");
        roomDetails += (getDescription() + "\n");

        if (items.size() != 0) {
            roomDetails += "Items: ";

            for (Item current : items) {
                String itemName = current.getName();

                roomDetails += itemName + "   ";
            }

            roomDetails += "\n";
        }

        roomDetails += "Exits: ";

        Iterator<String> iter = exits.keySet().iterator();

        while (iter.hasNext()) {
            String current = iter.next();

            if (getExit(current) != null) {
                roomDetails += current + " ";
            }
        }

        roomDetails += "\n";

        return roomDetails;
    }

    /**
     * Defines an exit from this room.
     * 
     * @param direction The direction of the exit.
     * @param neighbor The door in the given direction.
     */
    public void setExit(String direction, Door neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Gets a door in a specified direction if it exists.
     * 
     * @param direction The direction of the exit.
     * @return The door in the specified direction or null if it does not exist.
     */
    public Door getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Sets the number of points that a player will receive upon entering a room.
     * 
     * @param thePoints The number of points that a player will receive upon entering a room.
     */
    public void setPoints(int thePoints) {
        points = thePoints;
    }

    /**
     * Gets the number of points that a player will receive upon entering a room.
     * 
     * @return The number of points that a player will receive upon entering a room.
     */
    public int getPoints() {
        int roomPoints = points;
        setPoints(0);

        return roomPoints;
    }

    /**
     * Adds the specified item to the room.
     * 
     * @param theItem The item to be added.
     */
    public void addItem(Item theItem) {
        if (theItem instanceof Ingredient) {
            Ingredient ingredient = (Ingredient)theItem;
            String itemName = ingredient.getName();
            boolean found = false;

            for (Item current : items) { 
                String currentName = current.getName();

                if (itemName.equals(currentName)) {
                    found = true;

                    Ingredient anIngredient = (Ingredient)current;

                    anIngredient.setNumberInGroup(anIngredient.getNumberInGroup() + ingredient.getNumberInGroup());
                }
            }

            if (!found) {
                items.add(theItem);
            }
        }
        else {
            items.add(theItem);
        }
    }

    /**
     * Gets the specified item.
     * 
     * @param theName The name of the specified item.
     * @return The specified item, if it exists.
     */
    public Item getItem(String theName) {
        Item item = null;
        boolean found = false;
        Iterator<Item> iter = items.iterator();

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
     * Removes the specified item from the room.
     * 
     * @param theName The name of the specified item.
     * @return The specified item, if it exists.
     */
    public Item removeItem(String theName) {
        Item item = null;
        boolean found = false;
        Iterator<Item> iter = items.iterator();

        while (!found && iter.hasNext()) {
            Item current = iter.next();
            String itemName = current.getName();

            if (itemName.equals(theName)) {
                item = current;
                iter.remove();
                found = true;
            }
        }

        return item;
    }

    /**
     * Gets whether or not the specified item can be found in the room.
     * 
     * @param theName The name of the specified item.
     * @return Whether or not the specified item can be found in the room.
     */
    public boolean isInRoom(String theName) {
        Iterator<Item> iter = items.iterator();
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
     * Gets whether or not the specified item can be found in a container within the room.
     * 
     * @param theName The name of the specified item.
     * @return Whether or not the specified item can be found in a container within the room.
     */
    public boolean isInRoomContainer(String theName) {
        Iterator<Item> iter = items.iterator();
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
        Iterator<Item> iter = items.iterator();
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
    
    /**
     * Adds a character to the room.
     * 
     * @param theCharacter The character to be added.
     */
    public void addCharacter(Character theCharacter) {
        characters.add(theCharacter);
    }
    
    /**
     * Gets the specified character in the room.
     * 
     * @param theName The name of the specified character.
     * @return The specified character in the room.
     */
    public Character getCharacter(String theName) {
        Iterator<Character> iter = characters.iterator();
        boolean found = false;
        Character character = null;
        
        while (iter.hasNext() && !found) {
            Character current = iter.next();
            String characterName = current.getName();
            
            if (characterName.equals(theName)) {
                character = current;
            }
        }
        
        return character;
    }   
    
    /**
     * Removes the specified character from the room.
     * 
     * @param theName The name of the specified character.
     */
    public void removeCharacter(String theName) {
        Iterator<Character> iter = characters.iterator();
        boolean found = false;
        
        while (iter.hasNext() && !found) {
            Character current = iter.next();
            String characterName = current.getName();
            
            if (characterName.equals(theName)) {
                characters.remove(current);
            }
        }
    }
}
