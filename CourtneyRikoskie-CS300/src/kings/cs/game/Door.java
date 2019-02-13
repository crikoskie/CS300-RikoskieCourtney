package kings.cs.game;

/**
 * Class Door - a door or portal between two Rooms in an adventure game.
 * 
 * This class is part of the "Cat" application. "Cat" is a text-based adventure game.
 * 
 * A "Door" represents a door or portal between two locations of the game.
 * It stores a reference to the neighboring room and whether that door
 * or portal is locked.  Doors are not locked by default.
 * 
 * @author Maria Jump
 * @version 2015.02.01
 */
public class Door {
    /** The room that this door leads to. */
    private Room destination;
    /** Whether this door is locked. */
    private boolean locked;
    /** The key needed to unlock the door. */
    private Item key;

    /**
     * Constructor for the Door class.
     * @param destination The room this door leads to
     */
    public Door(Room destination) {
        this.destination = destination;
        this.locked = false;
        key = null;
    }

    /**
     * A getter for the room this door leads to.
     * @return The room this door leads to
     */
    public Room getDestination() {
        return destination;
    }

    /**
     * A getter for whether this door is locked.
     * @return Whether this door is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * A setter for whether this door is locked.
     * @param locked Whether this door is locked.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Gets the key needed to unlock the door.
     * 
     * @return The key needed to unlock the door.
     */
    public Item getKey() {
        return key;
    }
    
    /**
     * Sets the key needed to unlock the door.
     * 
     * @param theKey The key needed to unlock the door.
     */
    public void setKey(Item theKey) {
        key = theKey;
    }
}
