package kings.cs.game;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;

/**
 * Mock version of the Writer class. To be used when testing, so the program
 * does not attempt to print to screen.
 * 
 * @author Courtney Rikoskie
 */
public class MockWriter implements WriterInterface {

	@Override
	public void setTextArea(JTextPane text) {
	}

	@Override
	public void printCommand(String command) {
	}

	@Override
	public void println() {
	}

	@Override
	public void println(int toPrint) {
	}

	@Override
	public void print(int toPrint) {
	}

	@Override
	public void println(double toPrint) {
	}

	@Override
	public void print(double toPrint) {
	}

	@Override
	public void println(Object toPrint) {
	}

	@Override
	public void print(Object toPrint) {
	}

	@Override
	public void println(String toPrint) {
	}

	@Override
	public void print(String toPrint) {
	}

	@Override
	public void standardPrint(String toPrint) {
	}

	@Override
	public void printWithAttributes(SimpleAttributeSet attributes, String toPrint) throws IllegalStateException {
	}

	@Override
	public void restartLog() {
	}

	@Override
	public void copyDefaultLog() {
	}
}
