package kings.cs.game;

import javax.swing.JTextPane;
import javax.swing.text.Document;

public class TextPane implements TextPaneInterface {

	private JTextPane textArea;
	
	public TextPane(JTextPane pane) {
		textArea = pane;
	}
	
	@Override
	public void setEditable(boolean b) {
		textArea.setEditable(b);
	}

	@Override
	public void setCaretPosition(int position) {
		textArea.setCaretPosition(position);
	}

	@Override
	public Document getDocument() {
		return textArea.getDocument();
	}
	
	public JTextPane getTextArea() {
		return textArea;
	}
}
