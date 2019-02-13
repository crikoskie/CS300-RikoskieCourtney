package kings.cs.game;

/**
 * Represents the available commands within the game.
 * 
 * @author Courtney Rikoskie
 * @version Spring 2017
 */
public enum CommandEnum
{
    /** The go command. */
    GO("go"),
    /** The help command. */
    HELP("help"),
    /** The look command. */
    LOOK("look"),
    /** The quit command. */
    QUIT("quit"),
    /** The status command. */
    STATUS("status"),
    /** The back command. */
    BACK("back"),
    /** The drop command. */
    DROP("drop"),
    /** The inventory command. */
    INVENTORY("inventory"),
    /** The examine command. */
    EXAMINE("examine"),
    /** The take command. */
    TAKE("take"),
    /** The unlock command. */
    UNLOCK("unlock"),
    /** The lock command. */
    LOCK("lock"),
    /** The pack command. */
    PACK("pack"),
    /** The unpack command. */
    UNPACK("unpack"),
    /** The make command. */
    MAKE("make"),
    /** The empty command. */
    EMPTY("empty"),
    /** The pour command. */
    POUR("pour"),
    /** The read command. */
    READ("read"),
    /** The use command. */
    USE("use"),
    /** The talk command. */
    TALK("talk"),
    /** The trade command. */
    TRADE("trade");
    
    /** The text that the player enters in order to execute the command. */
    private String text;
    
    /**
     * Constructs a new CommandEnum.
     * 
     * @param theText The text that the player enters in order to execute the command.
     */
    private CommandEnum(String theText) {
        text = theText;
    }
    
    /**
     * Gets the text that the player enters in order to execute a command.
     * 
     * @return The text that the player enters in order to execute a command.
     */
    public String getText() {
        return text;
    }
}
