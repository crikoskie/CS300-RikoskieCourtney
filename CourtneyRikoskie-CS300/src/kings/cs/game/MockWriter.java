package kings.cs.game;

public class MockWriter implements WriterInterface {
	private String currentString;

	public void println(String toPrint) {
		setCurrentString(toPrint);
	}

	public void print(String toPrint) {
	
	}

	public void println() {

	}

	public void printCommand(String command) {

	}

	public String getCurrentString() {
		return currentString;
	}

	public void setCurrentString(String toPrint) {
		if (toPrint != null) {
			currentString = toPrint;
		}
	}
}
