package kings.cs.game;

public class MockReader implements ReaderInterface {
	private String reply;
	
	@Override
	public String getResponse(WriterInterface writer) {
		return reply;
	}

	@Override
	public Command getCommand(WriterInterface writer, CommandWords commandWords) {
		return null;
	}

	public void setReply(String theReply) {
		reply = theReply;
	}
}
