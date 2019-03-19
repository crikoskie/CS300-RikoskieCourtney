package kings.cs.game;

/**
 * This class is the main class of the "Cat" application.
 * "Cat" is a text-based adventure game.
 * 
 * This game class creates and initializes all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Maria Jump
 * @author Courtney Rikoskie
 * @version 2015.02.01
 */

public class Game {
    /** The world where the game takes place. */
    private World world;
    /** The score that the player currently has. */
    private int score;
    /** The number of turns that the player has taken. */
    private int turns;
    /** The player character. */
    private Player player;
    /** Ends the game if set to false. */
    private boolean wantToContinue;
    /** The total score. */
    private static final int TOTAL_SCORE;
    /** Used to output to the screen. */
    private WriterInterface writer;
    
    /**
     * Static initializer.
     */
    static {
        TOTAL_SCORE = 235;
    }

    /**
     * Create the game and initialize its internal map.
     */
    public Game() {
        world = makeWorld();
        // set the starting room
        player = makePlayer();
        score = 0;
        turns = 0;
        wantToContinue = true;
        writer = makeWriter();
    }

    protected World makeWorld() {
		return new World();
	}
    
    protected WriterInterface makeWriter() {
    	return new Writer();
    }
    
    protected Player makePlayer() {
    	return new Player(world.getRoom("Front Porch"));
    }

	/**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main game loop. Here we repeatedly read commands and
        // execute them until the game is over.
        boolean wantToQuit = false;
        while (!wantToQuit && wantToContinue) {
            Command command = Reader.getCommand(writer);
            wantToQuit = processCommand(command);
            turns += 1;
            // other stuff that needs to happen every turn can be added here.
        }
        printGoodbye();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helper methods for processing the commands

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command
     *            The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    protected boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            writer.println("I don't know what you mean...");
        } 
        else {
            CommandEnum commandWord = command.getCommandWord();

            switch(commandWord) {
                case HELP:
                    printHelp();
                    break;
                case GO:
                    goRoom(command);
                    break;
                case QUIT:
                    wantToQuit = quit(command);
                    break;
                case LOOK:
                    look();
                    break;
                case STATUS:
                    status();
                    break;
                case BACK:
                    back();
                    break;
                case DROP:
                    drop(command);
                    break;
                case INVENTORY:
                    inventory();
                    break;
                case EXAMINE:
                    examine(command);
                    break;
                case TAKE:
                    take(command);
                    break;
                case UNLOCK:
                    unlock(command);
                    break;
                case LOCK:
                    lock(command);
                    break;
                case PACK:
                    pack(command);
                    break;
                case UNPACK:
                    unpack(command);
                    break;
                case MAKE:
                    make(command);
                    break;
                case EMPTY:
                    empty(command);
                    break;
                case POUR:
                    pour(command);
                    break;
                case READ:
                    read(command);
                    break;
                case USE:
                    use(command);
                    break;
                case TALK:
                    talk(command);
                    break;
                case TRADE:
                    trade(command);
                    break;
                default:
                    writer.println(commandWord + " is not implemented yet!");
                    break;
            }
        }
        return wantToQuit;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helper methods for implementing all of the commands.
    // It helps if you organize these in alphabetical order.

    /**
     * Try to go to one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     * 
     * @param command
     *            The command to be processed.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            writer.println("Go where?");
        } 
        else {
            String direction = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            // Try to leave current.
            Door doorway = null;
            doorway = currentRoom.getExit(direction);
            Room weapons = world.getRoom("Fairsway Weapons");
            Room clearing = world.getRoom("Clearing");
            
            if (doorway == null) {
                writer.println("There is no door!");
            } 
            else if (doorway.isLocked()) {
                writer.println("You try your best to open the hatch, but it's locked and won't budge.");
            }
            else if (currentRoom.getName().equals("Front Porch") && direction.equals("down") && clearing.isInRoom("hidden barrier rune")) {
                writer.print("You step into the forest, determined and ready to face the world, but your confidence isn't well-placed.");
                writer.println(" Master comes out of the trees, a severe frown on her face. You probably should have gotten rid of that barrier somehow.");
                writer.println();
                        
                wantToContinue = false;
            }
            else if (currentRoom.getName().equals("In Front of Bridge") && direction.equals("east") && !player.isInInventory("citizenship card")) {
                writer.println("Guardsman: Turn around, Faye. And don't come back until you're a proper citizen.");
            }
            else if (currentRoom.getName().equals("South Path") && direction.equals("south") && !player.isInInventory("illuminated bulb") && !currentRoom.isInRoom("illuminated bulb")) {
                writer.println("This must be another one of Master's precautions. You can't see even a few feet in front of you. Best find something bright.");
            }
            else if (currentRoom.getName().equals("Fairsway Weapons") && !(weapons.isInRoom("broadsword") || weapons.isInRoom("duplicate broadsword"))) {
                writer.println("Tave: I don't mind a little mischief, but that sword doesn't leave this store.");
            }
            else if (currentRoom.getName().equals("North Path") && direction.equals("up") && player.isInInventory("cat")) {
                printWin();
                wantToContinue = false;
            }
            else {                               
                Room newRoom = doorway.getDestination();                    
                player.setCurrentRoom(newRoom);          
                    
                if (newRoom.getName().equals("South Path") && (player.isInInventory("illuminated bulb") || newRoom.isInRoom("illuminated bulb"))) {
                    newRoom.setActive(1);
                }
                if (newRoom.getName().equals("Alley") && player.isInInventory("illuminated bulb")) {
                    Item bulb = player.removeItem("illuminated bulb");
                    currentRoom.addItem(bulb);
                    writer.println("You leave the illuminated bulb behind.\n");
                }
                    
                if (newRoom.getName().equals("Welcome to the Citizen District") && !weapons.isInRoom("broadsword")) {
                    weapons.removeCharacter("tave");
                        
                    Room taveHouse = world.getRoom("Tave's House");
                    Character tave = world.getCharacter("tave");
                    taveHouse.addCharacter(tave);
                        
                    Conversation taveSecondCon = new Conversation("Tave", "I don't feel like talking right now");
                    taveSecondCon.addReply("hi", "Someone stole my broadsword.\n\n\tA: I'm sorry\n\tB: Who would do that?\n");
                    taveSecondCon.addReply("hia", "Faye, I--I don't feel like talking right now.");
                    taveSecondCon.addReply("hib", "Faye, I--I don't feel like talking right now.");
                        
                    tave.setResponses(taveSecondCon);
                    taveHouse.setActive(1);
                    weapons.setActive(1);
                }
                
                int roomPoints = newRoom.getPoints();
                score += roomPoints;

                printLocationInformation();
            }
        }
    }

    /**
     * Prints out the winning message.
     */
    private void printWin() {
        writer.println("As soon as your feet touch the porch, you breathe a sign of relief.  Master would have appeared by now if she had realized you left.  It seems you got away with at least this one thing.");
        writer.println();
    }
    
    /**
     * Print out the closing message for the player.
     */
    private void printGoodbye() {
        writer.println("You have earned " + score + " points out of " + TOTAL_SCORE + " in " + turns + " turns.");
        writer.println("Thank you for playing.");
    }

    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        writer.println("You idle for a bit.");
        writer.println();

        String commands = CommandWords.getCommandString();
        writer.println(commands);
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        writer.println();
        writer.print("You flip to the next page of your book. Your eyes skim the page, unfocused, and the chair creaks as you rock back and forth.\n\nIt's been a while since you've felt this bored.\n\n");
        writer.print("Your master has gone off into town without you for once, and you have been left alone in your little cottage in the woods. Relocating yourself to the front porch has done little to alleviate the mind-numbing-ness of The Uses of Wratagrass, "); 
        writer.print("but there was quite literally nothing else to do, barred from making potions as you were. You decide to take a break and close the book with a thwump. Your neck is a bit stiff from looking down for so long, which you pout about. \n\n");
        writer.print("'I'm only ten,' you grumble.\n\nThe green of the forest is a welcome sight, and you're tempted to go for a walk against your master's wishes until you see a clear shimmering wall reflected in the light.");
        writer.print(" Indignation rises up inside of you. \n\nA barrier. Most likely to warn Master if you leave, as if the babysitter wasn't enough. Even now, you can feel the invisible eyes of Master's cat watching your every move. \n\n");
        writer.print("'I'm already ten!' you say.\n\nThen, as if purposefully trying to worsen your mood, your own cat appears along the edge of the forest, past the barrier, somewhere he definitely wasn't supposed to be.\n\n");
        writer.print("You jump up. 'What are you doing? If you get spirited away or something, I'm the one who's going to get into trouble!' \n\nHe stares at you for a moment before turning with utmost indifference toward the forest.");
        writer.print("\n\n'Stop!' You scramble toward him, but by the time you reach the beginnings of the barrier, he's disappeared into the trees.");
        writer.println();
        writer.println();
        printLocationInformation();
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     *
     * @param command
     *            The command to be processed.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        boolean wantToQuit = true;
        if (command.hasSecondWord()) {
            writer.println("Quit what?");
            wantToQuit = false;
        }
        return wantToQuit;
    }

    /**
     * Prints out the current location and exits.
     */
    private void printLocationInformation() { 
        Room currentRoom = player.getCurrentRoom();

        writer.println(currentRoom.toString());
    }

    /**
     * Prints out the location information.
     */
    private void look() {
        printLocationInformation();
    }

    /**
     * Prints out the player's status.
     */
    private void status() {
        writer.println("You have earned " + score + " points in " + turns + " turns.");
        writer.println();
        writer.println(player.getCurrentRoom().toString());
    }

    /**
     * Takes the player back to the previous room, if it exists.
     */
    private void back() {
        Room previous = player.getPreviousRoom();

        if (previous != null) {
            player.setCurrentRoom(previous);
            writer.println(player.getCurrentRoom().toString());
        }
        else {
            writer.println("You cannot go back.");
        }
    }

    /**
     * Drops an item from the player character's inventory.
     * 
     * Command command The command to be processed.
     */
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to drop?");
        }
        else {
            String itemName = command.getRestOfLine();

            if (player.isInInventory(itemName)) {
                Item item = player.getItem(itemName);
                player.removeItem(itemName);
                player.getCurrentRoom().addItem(item);
                writer.println("Dropped.");
            }
            else {
                writer.println("You are not carrying this item.");
            }
        }
    }

    /**
     * Prints out the player character's inventory.
     */
    private void inventory() {
        writer.println(player.toString());        
    }

    /**
     * Examines a specified item.
     * 
     * @param command The command to be processed.
     */
    private void examine(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to examine?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (player.isInInventory(itemName)) {
                Item item = player.getItem(itemName);
                writer.println(item.toString());
            }
            else if (currentRoom.isInRoom(itemName)) {
                Item item = currentRoom.getItem(itemName);
                writer.println(item.toString());
            }
            else {
                writer.println("You search the room and your pockets, but there is no such item to be found.");
            }
        }
    }

    /**
     * Takes a specified item.
     * 
     * @param command The command to be processed.
     */
    private void take(Command command) {
        String result = "Taken.";
        
        if (!command.hasSecondWord()) {
            writer.println("What would you like to take?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (currentRoom.isInRoom(itemName)) {
                Item item = currentRoom.getItem(itemName);
                boolean added = player.addToInventory(item);

                if (added) {
                    score += item.getPointValue();
                    
                    if (item instanceof Ingredient) {
                        Ingredient ingredient = (Ingredient)item;

                        if (ingredient.getNumberInGroup() < 3 && currentRoom.getName().equals("Backyard") && !ingredient.getName().equals("orreamin")) {
                            currentRoom.setActive(1);
                        }
                        if (ingredient.getNumberInGroup() == 0) {
                            currentRoom.removeItem(itemName);
                        }
                        if (currentRoom.getName().equals("Backyard") && !currentRoom.isInRoom(itemName)) {
                            writer.print("You've made quite a mess of the garden, and you're master is sure to notice.");
                            writer.println(" She'll definitely think that you're irresponsible. Even if you do get your cat back, what's the point?");
                            wantToContinue = false;
                            result = "";
                        }
                    }
                    else {
                        currentRoom.removeItem(itemName);
                    }

                    writer.println(result);
                }
                else {
                    if (item.getWeight() > Player.MAX_WEIGHT) {
                        writer.println("This item is too heavy for you to pick up.");
                    }
                    else {
                        writer.println("You try to pick it up, but you're already carrying so much.");
                    }
                }
            }
            else {
                writer.println("You search the room, but there is no such item to be found.");
            }
        }
    }

    /**
     * Unlocks a specified door.
     * 
     * @param command The command to be processed.
     */
    private void unlock(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to unlock?");
        }
        else {
            String direction = command.getRestOfLine();
            Room room = player.getCurrentRoom();
            Door door = room.getExit(direction);

            if (door == null) {
                writer.println("There is no door in that direction.");
            }
            else {
                if (door.isLocked()) {
                    writer.println("With which key?");
                    String response = Reader.getResponse(writer);
                    Item key = door.getKey();
                    String keyName = key.getName();

                    if (player.isInInventory(response)) {
                        if (response.equals(keyName)) {
                            door.setLocked(false);
                            writer.println("You unlocked the door.");
                        }
                        else {
                            writer.println("The key doesn't fit the lock.");
                        }
                    }
                    else {
                        writer.println("You don't have that key.");
                    }
                }
                else {
                    writer.println("The door is already unlocked."); 
                }
            }
        }
    }

    /**
     * Locks a specified door.
     * 
     * @param command The command to be processed.
     */
    private void lock(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to lock?");
        }
        else {
            String direction = command.getRestOfLine();
            Room room = player.getCurrentRoom();
            Door door = room.getExit(direction);

            if (door == null) {
                writer.println("There is no door in that direction.");
            }
            else {
                if (door.isLocked()) {
                    writer.println("The door is already locked.");
                }
                else {
                    Item key = door.getKey();

                    if (key == null) {
                        writer.println("The door cannot be locked.");
                    }
                    else {
                        writer.println("With which key?");
                        String keyName = key.getName();
                        String response = Reader.getResponse(writer);

                        if (player.isInInventory(response)) {
                            if (response.equals(keyName)) {
                                door.setLocked(true);
                                writer.println("You locked the door.");
                            }
                            else {
                                writer.println("That is not the right key.");
                            }
                        }
                        else {
                            writer.println("You don't have that key.");
                        }
                    }
                }
            }
        }
    }

    /**
     * Packs the specified item into a container.
     * 
     * @param command The command to be processed.
     */
    private void pack(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to pack?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!(currentRoom.isInRoom(itemName) || player.isInInventory(itemName))) {
                writer.println("You can't pack an item you don't have.");
            }
            else {
                Item item = null;

                if (currentRoom.isInRoom(itemName)) {
                    item = currentRoom.getItem(itemName);
                }
                if (player.isInInventory(itemName)) {
                    item = player.getItem(itemName);
                }

                if (item.getWeight() > Player.MAX_WEIGHT) {
                    writer.println("This item is too heavy for you to move.");
                }
                else {
                    writer.println("Into which container?");

                    String response = Reader.getResponse(writer);

                    if (!(currentRoom.isInRoom(response) || player.isInInventory(response))) {
                        writer.println("You don't see that container anywhere.");
                    }
                    else {
                        Item container = null;

                        if (currentRoom.isInRoom(response)) {
                            container = currentRoom.getItem(response);
                        }
                        if (player.isInInventory(response)) {
                            container = player.getItem(response);
                        }

                        if (!(container instanceof Container) || container instanceof PotionContainer) {
                            writer.println("You can't pack things in that.");
                        }
                        else if (container instanceof HerbContainer) {
                            HerbContainer herbContainer = (HerbContainer)container;
                            String containerName = herbContainer.getName();
                            
                            if (!(item instanceof Ingredient)) {
                                writer.println("You probably shouldn't try to put that in there.");
                            }
                            else {
                                Ingredient ingredient = (Ingredient)item;
                                String ingredientName = ingredient.getName();
                                
                                if (currentRoom.isInRoom(ingredientName) && player.isInInventory(containerName)) {
                                    if (ingredient.getWeight() + player.getTotalWeight() > Player.MAX_WEIGHT) {
                                        writer.println("You try to pick it up, but you're already carrying so much.");
                                    }
                                    else if (currentRoom.getName().equals("Backyard") && !player.isInInventory(ingredientName)) {
                                        writer.println("You probably shouldn't rip out all the herbs at once.");
                                    }
                                    else {
                                        writer.println("Packed.");
                                        herbContainer.addItem(item);
                                        score += item.getPointValue();
                                        player.removeItem(ingredientName);
                                    }
                                } 
                                else {
                                    if (currentRoom.getName().equals("Backyard") && !player.isInInventory(ingredientName)) {
                                        writer.println("You probably shouldn't rip out all the herbs at once.");
                                    }
                                    else {
                                        writer.println("Packed.");
                                        herbContainer.addItem(item);
                                        score += item.getPointValue();
                                        player.removeItem(ingredientName);
                                    }
                                }
                            }
                        }
                        else {
                            Container aContainer = (Container)container;
                            String containerName = aContainer.getName();
                            
                            if (item instanceof Ingredient) {
                                writer.println("You probably shouldn't try to put that in there.");
                            }
                            else {
                                if (!(currentRoom.isInRoom(itemName) && player.isInInventory(containerName))) {
                                    player.removeItem(itemName);
                                    aContainer.addItem(item);
                                    writer.println("Packed.");
                                }
                                else {
                                    if (item.getWeight() + player.getTotalWeight() > Player.MAX_WEIGHT) {
                                        writer.println("You try to pick it up, but you're already carrying so much.");
                                    }
                                    else {
                                        currentRoom.removeItem(itemName);
                                        aContainer.addItem(item);
                                        score += item.getPointValue();
                                        writer.println("Packed.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Unpacks the specified item from a container.
     * 
     * @param command The command to be processed.
     */
    private void unpack(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to unpack?");
        }
        else {
            String containerName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!(currentRoom.isInRoom(containerName) || player.isInInventory(containerName))) {
                writer.println("You search the room and your pockets, but there is no such item to be found.");
            }
            else {
                Item container = null;

                if (currentRoom.isInRoom(containerName)) {
                    container = currentRoom.getItem(containerName);
                }
                if (player.isInInventory(containerName)) {
                    container = player.getItem(containerName);
                }

                if (!(container instanceof Container) || container instanceof PotionContainer) {
                    writer.println("You can't unpack that.");
                }
                else {
                    writer.println("What item would you like to unpack from the " + containerName + "?");
                    String response = Reader.getResponse(writer);
                    Container aContainer = (Container)container;

                    if (!aContainer.isInContainer(response)) {
                        writer.println("You can't find that item in the " + containerName + ".");
                    }
                    else {
                        Item item = aContainer.getItem(response);

                        if (currentRoom.isInRoom(containerName)) {
                            if (item.getWeight() + player.getTotalWeight() > Player.MAX_WEIGHT) {
                                writer.println("You try to pick it up, but you're already carrying so much.");
                            }
                            else {
                                player.addToInventory(item);
                                score += item.getPointValue();
                                if (item instanceof Ingredient && aContainer instanceof HerbContainer) {
                                    Ingredient ingredient = (Ingredient)item;
                                    HerbContainer herbContainer = (HerbContainer)aContainer;

                                    if (ingredient.getNumberInGroup() == 0) {
                                        herbContainer.removeItem(response);
                                    }
                                }
                                else {
                                    aContainer.removeItem(response);
                                }
                                writer.println("Unpacked.");
                            }
                        }
                        else {
                            player.addToInventory(item);
                            score += item.getPointValue();
                            if (item instanceof Ingredient && aContainer instanceof HerbContainer) {
                                Ingredient ingredient = (Ingredient)item;
                                HerbContainer herbContainer = (HerbContainer)aContainer;
                                
                                if (ingredient.getNumberInGroup() == 0) {
                                    herbContainer.removeItem(response);
                                }
                            }
                            else {
                                aContainer.removeItem(response);
                            }
                            writer.println("Unpacked.");
                        }
                    }
                }
            }
        }
    }

    /**
     * Makes the specified item.
     * 
     * @param command The command to be processed.
     */
    private void make(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to make?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!currentRoom.getName().equals("Cellar")) {
                writer.println("You look around, but there is nothing here you can use to make potions.");
            }
            else {
                Item item = world.getPotion(itemName);

                if (item == null) {
                    writer.println("That isn't something you can make.");
                }
                else {
                    Potion potion = (Potion)item;
                    Item container = currentRoom.getItem("cauldron");
                    PotionContainer cauldron = (PotionContainer)container;

                    if (!cauldron.isEmpty()) {
                        writer.println("There's already something in the cauldron.");
                    }
                    else {
                        Container herbPouch = null;

                        if (player.isInInventory("herb pouch")) {
                            herbPouch = player.getContainer("herbPouch");
                        }
                        else if (currentRoom.isInRoom("herb pouch")) {
                            herbPouch = currentRoom.getContainer("herbPouch");
                        }

                        writer.println(potion.makePotion(player, currentRoom, world, herbPouch, cauldron));
                        
                        if (potion.wasMade()) {
                            score += potion.getPointValue();
                        }
                    }
                }
            }
        }
    }

    /**
     * Empties certain containers.
     * 
     * @param command The command to be processed.
     */
    private void empty(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to empty?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!(player.isInInventory(itemName) || currentRoom.isInRoom(itemName))) {
                writer.println("You search the room and your pockets, but there is no such item to be found.");
            }
            else {
                Item item = null;

                if (player.isInInventory(itemName)) {
                    item = player.getItem(itemName);
                }
                else {
                    item = currentRoom.getItem(itemName);
                }

                if (!(item instanceof PotionContainer)) {
                    writer.println("Why would you want to empty that?");
                }
                else {
                    PotionContainer container = (PotionContainer)item;

                    if (container.isEmpty()) {
                        writer.println("It's already empty.");
                    }
                    else {
                        writer.println(container.empty());
                    }
                }
            }
        }
    }

    /**
     * Pours an item from one container into another.
     * 
     * @param command The command to be processed.
     */
    private void pour(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What item from a container would you like to pour?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!(currentRoom.isInRoomContainer(itemName) || player.isInInventoryContainer(itemName))) {
                writer.println("That isn't in any of the nearby containers.");
            }
            else {
                Item item = null;
                Container container = null;

                if (currentRoom.isInRoomContainer(itemName)) {
                    container = currentRoom.getContainer(itemName);
                    item = container.getItem(itemName);
                }
                if (player.isInInventoryContainer(itemName)) {
                    container = player.getContainer(itemName);
                    item = container.getItem(itemName);
                }

                if (!(item instanceof Potion)) {
                    writer.println("Why would you want to pour that?");
                }
                else {
                    Potion potion = (Potion)item;

                    writer.println("What would you like to pour it into?");

                    String response = Reader.getResponse(writer);

                    if (!(player.isInInventory(response) || currentRoom.isInRoom(response))) {
                        writer.println("You search the room and your pockets, but there is no such item to be found.");
                    }
                    else {
                        Item secondItem = null;

                        if (currentRoom.isInRoom(response)) {
                            secondItem = currentRoom.getItem(response);
                        }
                        if (player.isInInventory(response)) {
                            secondItem = player.getItem(response);
                        }

                        if (!(secondItem instanceof PotionContainer)) {
                            writer.println("Why would you want to pour into that?");
                        }
                        else {
                            PotionContainer potionContainer = (PotionContainer)secondItem;

                            if (!potionContainer.isEmpty()) {
                                writer.println("There's already something inside of that.");
                            }
                            else {
                                PotionContainer previous = potion.getContainer();
                                if (previous != null) {
                                    previous.empty();
                                }
                                writer.println(potionContainer.addPotion(potion));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Reads a book.
     * 
     * @param command The command to be processed.
     */
    private void read(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to read?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!(currentRoom.isInRoom(itemName) || player.isInInventory(itemName))) {
                writer.println("You search the room and your pockets, but there is no such item to be found.");
            }
            else {
                Item item = null;

                if (currentRoom.isInRoom(itemName)) {
                    item = currentRoom.getItem(itemName);
                }
                if (player.isInInventory(itemName)) {
                    item = player.getItem(itemName);
                }

                if (!(item instanceof Book)) {
                    writer.println("Um, you can't read that.");
                }
                else {
                    Book book = (Book)item;

                    writer.println(book.read());
                    writer.println("What would you like to read about?");

                    String response = Reader.getResponse(writer);

                    writer.println(book.goTo(response));
                }
            }
        }
    }

    /**
     * Uses an item.
     * 
     * @param command The command to be processed.
     */
    private void use(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What would you like to use?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!(currentRoom.isInRoomContainer(itemName) || player.isInInventoryContainer(itemName))) {
                writer.println("That isn't in any of the nearby containers.");
            }
            else {
                Item item = null;
                Container container = null;

                if (currentRoom.isInRoomContainer(itemName)) {
                    container = currentRoom.getContainer(itemName);
                    item = container.getItem(itemName);
                }
                if (player.isInInventoryContainer(itemName)) {
                    container = player.getContainer(itemName);
                    item = container.getItem(itemName);
                }

                if (!(item instanceof Potion)) {
                    writer.println("You can't use that.");
                }
                else {
                    Potion potion = (Potion)item;

                    writer.println("What would you like to use it on?");

                    String response = Reader.getResponse(writer);

                    if (!(currentRoom.isInRoom(response) || player.isInInventory(response))) {
                        writer.println("You search the room and your pockets, but there is no such item to be found.");
                    }
                    else {
                        Item secondItem = null;

                        if (currentRoom.isInRoom(response)) {
                            secondItem = currentRoom.getItem(response);
                        }
                        if (player.isInInventory(response)) {
                            secondItem = player.getItem(response);
                        }

                        writer.println(potion.use(player, currentRoom, secondItem, world));
                    }
                }
            }
        }
    }

    /**
     * Begans a conversation with the specified non-player character.
     * 
     * @param command The command to be processed.
     */
    private void talk(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("Who do you want to talk to?");
        }
        else {
            String characterName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();
            Character character = currentRoom.getCharacter(characterName);

            if (character == null) {
                writer.println("Um, who?");
            }
            else {
                Conversation conversation = character.getResponses();
                conversation.startConversation("hi", writer);
            }
        }
    }

    /**
     * Trades an item in the player's inventory for one in a non-player character's.
     * 
     * @param command The command to be processed.
     */
    private void trade(Command command) {
        if (!command.hasSecondWord()) {
            writer.println("What do you want to trade?");
        }
        else {
            String itemName = command.getRestOfLine();
            Room currentRoom = player.getCurrentRoom();

            if (!(player.isInInventory(itemName))) {
                writer.println("You search your pockets, but there is no such item to be found.");
            }
            else {
                Item item = player.getItem(itemName);

                writer.println("Who do you wish to trade with?");

                String response = Reader.getResponse(writer);
                Character character = currentRoom.getCharacter(response);

                if (character == null) {
                    writer.println("Um, who?");
                }
                else {
                    Item npcItem = character.getInventory();
                    
                    if (npcItem == null) {
                        writer.println("They don't have anything to trade.");
                    }
                    else if (npcItem.getWeight() + player.getTotalWeight() < Player.MAX_WEIGHT) {
                        if (character.isTradeItem(itemName)) {
                            if (character.getName().equals("forest guardian")) {
                                Item tradeItem = character.getTradeItem(itemName);
                                int npcItemValue = npcItem.getPointValue();
                                
                                if (tradeItem instanceof PotionContainer) {
                                    PotionContainer potionContainer = (PotionContainer)tradeItem;
                                    Potion potion = potionContainer.getPotion();
                                    
                                    if (potion != null) {
                                        if (potion.getName().equals("unknown potion")) {
                                            player.addToInventory(npcItem);
                                            player.removeItem(itemName);
                                            character.setInventory(null);
                                            score += npcItemValue;
                                            writer.println(character.getName() + ": " + character.getTradeMessage());
                                        }
                                        else {
                                            writer.println("Forest Guardian: I'm sure you know this, but that's the wrong potion."); 
                                        }
                                    }
                                    else {
                                        writer.println("Forest Guardian: There's nothing in this, Faye.");
                                    }
                                }
                            }
                            else {
                                int npcItemValue = npcItem.getPointValue();
                                
                                player.addToInventory(npcItem);
                                player.removeItem(itemName);
                                character.setInventory(null);
                                score += npcItemValue;
                                writer.println(character.getName() + ": " + character.getTradeMessage());
                            }
                        }
                        else {
                            writer.println("They don't want to trade for that.");
                        }
                    }
                    else {
                        writer.println("You can't carry their item along with what you already have.");
                    }
                }
            }
        }
    }
    
    /**
     * Gets the game world.
     * 
     * @return The game world.
     */
    protected World getWorld() {
    	return world;
    }
    
    /**
     * Gets the player.
     * 
     * @return The player.
     */
    protected Player getPlayer() {
    	return player;
    }

    /**
     * Gets whether or not the player has lost.
     * 
     * @return Whether or not the player has lost.
     */
	protected boolean getWantToContinue() {
		return wantToContinue;
	}
}
