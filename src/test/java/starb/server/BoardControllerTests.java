package starb.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import starb.domain.game.Board;
import starb.server.controller.BoardController;
import starb.server.repo.BoardRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BoardControllerTests {
    private Board Board;
    @Autowired
    private BoardRepository repo;
    @Autowired
    private BoardController controller;
    @BeforeEach
    void setUp() {
        repo.deleteAll();

    }
    @Test
    public void testGetBoards() {

    }




}
