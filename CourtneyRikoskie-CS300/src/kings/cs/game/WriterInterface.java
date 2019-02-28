package kings.cs.game;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;

public interface WriterInterface {
	/**
	 * Mutator for the text component.
	 * 
	 * @param text
	 *            The text component.
	 */
	abstract void setTextArea(JTextPane text);

	/**
	 * Print the command that was input in blue.
	 * 
	 * @param command
	 *            The text contained in the command.
	 */
	abstract void printCommand(String command);

	/**
	 * Prints an empty line.
	 */
	abstract void println();

	/**
	 * Prints out a single integer to a line.
	 * 
	 * @param toPrint
	 *            The integer to print.
	 */
	abstract void println(int toPrint);

	/**
	 * Prints out a single integer.
	 * 
	 * @param toPrint
	 *            The integer to print.
	 */
	abstract void print(int toPrint);

	/**
	 * Prints out a double to a line.
	 * 
	 * @param toPrint
	 *            The double to print.
	 */
	abstract void println(double toPrint);

	/**
	 * Prints out a double.
	 * 
	 * @param toPrint
	 *            The double to print.
	 */
	abstract void print(double toPrint);

	/**
	 * Prints out an object to a line.
	 * 
	 * @param toPrint
	 *            The object to print.
	 */
	abstract void println(Object toPrint);

	/**
	 * Prints out a object.
	 * 
	 * @param toPrint
	 *            The object to print.
	 */
	abstract void print(Object toPrint);

	/**
	 * Prints a string after word-wrapping it to 80 characters if possible. Note
	 * that this fails to calculate correct widths if the string contains tabs.
	 * Ends with a line return.
	 *
	 * @param toPrint
	 *            The String to print.
	 */
	abstract void println(String toPrint);

	/**
	 * Prints a string after word-wrapping it to 80 characters if possible. Note
	 * that this fails to calculate correct widths if the string contains tabs.
	 * 
	 * @param toPrint
	 *            The String to print.
	 */
	abstract void print(String toPrint);
	
	/**
	 * Helper method for standard printing.
	 * 
	 * @param toPrint
	 *            The String to print.
	 */
	abstract void standardPrint(String toPrint);

	/**
	 * Helper method printing with attributes.
	 *
	 * @param attributes
	 *            A set of attributes to use when printing.
	 * @param toPrint
	 *            The String to print.
	 * @throws IllegalStateException
	 *             If the text area has not been set and we are trying to print
	 *             to it.
	 */
	abstract void printWithAttributes(SimpleAttributeSet attributes, String toPrint) throws IllegalStateException;
	
	/**
	 * Restart the default log.
	 */
	abstract void restartLog();

	/**
	 * Copy the default log.
	 */
	abstract void copyDefaultLog();	
}
