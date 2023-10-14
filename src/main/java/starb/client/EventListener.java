package starb.client;

/**
 *  An interface for classes that want to listen for events.
 *
 */
public interface EventListener {
    void onEvent(String event, Object... args);

}
