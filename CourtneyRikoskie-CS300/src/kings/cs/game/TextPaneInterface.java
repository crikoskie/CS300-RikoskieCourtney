package kings.cs.game;

import javax.swing.text.Document;

public interface TextPaneInterface {
	public void setEditable(boolean b);
	public void setCaretPosition(int position);
	public Document getDocument();
}
