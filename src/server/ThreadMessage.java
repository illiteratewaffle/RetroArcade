package server;

/**
 * This class will be used as a basis for communication between threads
 */
public class ThreadMessage {
    private final Thread sender;
    private final String content;

    /**
     * Constructor for the Message class
     * @param sender The thread is sending the given information
     * @param content The information that you want to send to that thread
     */
    public ThreadMessage(Thread sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    /**
     * Gives the thread of the sender
     * @return the Thread of the sender
     */
    public Thread getSender() {
        return sender;
    }

    /**
     * Get the content of the message
     * @return a String that is the content of the message
     */
    public String getContent() {
        return content;
    }
}
