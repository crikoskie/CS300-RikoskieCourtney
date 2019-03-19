package kings.cs.game;

/**
 * Allows the user to use a potion.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public interface Useable {
    /**
     * Uses a potion on specified item.
     * 
     * @param player The player character.
     * @param room The room that the player character is currently in.
     * @param theItem The specified item.
     * @param world The world.
     * @return The effects of the use.
     */
    public String use(Player player, Room room, Item theItem, WorldInterface world);
}
