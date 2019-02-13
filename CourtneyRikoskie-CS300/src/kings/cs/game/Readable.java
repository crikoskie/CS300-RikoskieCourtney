package kings.cs.game;

/**
 * 
 * 
 * @author Courtney Rikoskie 
 * @version Spring 2017
 */
public interface Readable {
    /**
     * Gets the first page of the book and the content within it.
     * 
     * @return The first page of the book and the content within it.
     */
    public String read();
    
    /**
     * Gets the content of the specified page.
     * 
     * @param page The specified page.
     * @return The content of the specified page
     */
    public String goTo(String page);
}
