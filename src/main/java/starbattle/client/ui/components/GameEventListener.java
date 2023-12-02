package starbattle.client.ui.components;

public interface GameEventListener {

    enum EVENT_TYPE {
        PUZZLE_SOLVED,
    }

    void onEvent(EVENT_TYPE eventType, Object... params) throws Exception;
}
