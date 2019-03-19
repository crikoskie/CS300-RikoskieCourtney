package kings.cs.game;

public interface ReaderInterface {
	public String getResponse(WriterInterface writer);
	
	public Command getCommand(WriterInterface writer);
}
