package server;

/**
 * This class will be used as a basis for communication between threads
 */
public class Message {
    private final long recipient; // The thread ID?
    private final String content;

    /**
     * Constructor for the Message class
     * @param recipient The thread you want to send information to
     * @param content The information that you want to send to that thread
     */
    public Message(Thread recipient, String content) {
        this.recipient = recipient.getId();
        this.content = content;
    }

    /**
     * Allows a thread to quickly check if they are the recipent
     * @param currentThread Should be the thread calling this function
     * @return boolean of whether or not they are the designated recipent
     */
    public boolean isRecipient(Thread currentThread) {
        return currentThread.threadId() == recipient;
    }

    /**
     * Get the content of the message
     * @return a String that is the content of the message
     */
    public String getContent() {
        return content;
    }
}
