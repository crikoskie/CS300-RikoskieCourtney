package kings.cs.game;

import java.util.HashMap;
import java.util.Iterator;
/**
 * This class is part of the "Campus of Kings" application. "Campus of Kings" is a
 * very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game. It is
 * used to recognize commands as they are typed in.
 * 
 * @author Maria Jump
 * @version 2015.02.01
 */

public class CommandWords {
    /** A constant array that holds all valid command words. */
    private static HashMap<String, CommandEnum> validCommands;

    /**
     * Static block to initialize the fields of CommandWords.
     */
    static {
        CommandEnum[] enumCommands = CommandEnum.values();        
        HashMap<String, CommandEnum> tempCommands = new HashMap<String, CommandEnum>(); 
        
        for (int index = 0; index < enumCommands.length; index += 1) {
            String textCommand = enumCommands[index].getText();
            tempCommands.put(textCommand, enumCommands[index]);
        }
        
        validCommands = tempCommands;
    }

    /**
     * Check whether a given String is a valid command word.
     * 
     * @param aString The string to determine whether it is a valid command.
     * @return true if a given string is a valid command, false if it isn't.
     */
    public static boolean isCommand(String aString) {
        boolean valid = false;
        CommandEnum command = validCommands.get(aString);
        
        if (command != null) {
            valid = true;
        }
        // if we get here, the string was not found in the commands
        return valid;
    }
    
    /**
     * Returns a list of the available commands, of the form:
     *      Your command words are:
     *          look go quit help
     *          
     * @return A string containing the list of available commands.
     */
    public static String getCommandString() {
        String commands = "Your command words are:" + "\n" + "   ";
        Iterator<String> iter = validCommands.keySet().iterator();
        
        while (iter.hasNext()) {
            String current = iter.next();
            
            commands += " " + current;
        }
        
        return commands;
    }
    
    /**
     * Converts a String into a CommandEnum object.
     * 
     * @param theString The String containing the command word.
     * @return The CommandEnum object representing the command, or null if the command 
          does not exisit
     */
    public static CommandEnum getCommand(String theString) {
        return validCommands.get(theString);
    }
}
