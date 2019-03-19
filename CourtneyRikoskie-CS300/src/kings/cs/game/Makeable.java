package kings.cs.game;

/**
 * Allows the user to make potions.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public interface Makeable {
    /**
     * Adds an ingredient needed to make the potion.
     * 
     * @param ingredient An ingredient needed to make the potion.
     */
    public void addIngredient(Ingredient ingredient);
    
    /**
     * Makes a potion, if all needed ingredients are available.
     * 
     * @param player The player character.
     * @param room The current room.
     * @param world The world.
     * @param container A container that ingredients may be found in.
     * @param cauldron The container which holds newly made potions.
     * @return Whether making the potion was successful.
     */
    public String makePotion(Player player, Room room, WorldInterface world, Container container, PotionContainer cauldron);    
}
