package starb.server.bootstrap;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import starb.domain.json.User;
import starb.server.repo.UserRepository;


@Component
public class UserBootstrap {
    private static final String BOARD_DATA_FILE = "Assets/Boards/boards.json";
    private UserRepository repo;

    public UserBootstrap(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * This method is called automatically, shortly after the server is started.
     * It checks to see if the database is empty, and if so, loads data from the JSON
     * file into the database.  Otherwise, it does nothing.
     *
     * @param event ignored
     */
    @EventListener
    public void onApplicationStart(ApplicationReadyEvent event) {
        // If the database already has data, do nothing.
        if( repo.count() > 0 ) return;
        // Add a new user to the database
        System.out.println("Loading user into DB.");
        repo.save(new User());
    }
}
