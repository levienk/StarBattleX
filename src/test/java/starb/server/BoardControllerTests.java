package starb.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;
import starb.domain.game.Board;
import starb.server.controller.BoardController;
import starb.server.repo.BoardRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BoardControllerTests {
    private Board board1;
    private Board board2;
    @Autowired
    private BoardRepository repo;
    @Autowired
    private BoardController controller;
    @BeforeEach
    void setUp() {
        repo.deleteAll();
        board1 = new Board(5, 5, new ArrayList<>(), new ArrayList<>(), 1, 1);
        board2 = new Board(5, 5, new ArrayList<>(), new ArrayList<>(), 1, 2);

        repo.save(board1);
        repo.save(board2);
    }
    @Test
    public void testPostBoard() {
        Board newBoard = new Board(5, 5, new ArrayList<>(), new ArrayList<>(), 1, 3);

        Board savedBoard = controller.postBoard(newBoard);

        assertNotNull(savedBoard);
        assertEquals(newBoard.getID(), savedBoard.getID());
    }

    @Test
    public void testGetBoards() {
        ArrayList<Integer> ids = controller.getBoards();

        assertNotNull(ids);
        assertEquals(2, ids.size());
        assertTrue(ids.contains(board1.getID()));
        assertTrue(ids.contains(board2.getID()));
    }
    @Test
    public void testGetBoardsWithEmptyRepo() {
        repo.deleteAll();
        ArrayList<Integer> ids = controller.getBoards();

        assertNotNull(ids);
        assertEquals(0, ids.size());
        assertFalse(ids.contains(board1.getID()));
        assertFalse(ids.contains(board2.getID()));
    }
    @Test
    public void testGetBoard() {
        String boardId = String.valueOf(board1.getID());

        Board foundBoard = controller.getBoard(boardId);

        assertNotNull(foundBoard);
        assertEquals(board1.getID(), foundBoard.getID());

    }
    @Test
    public void testGetBoardNotFound() {
        String invalidBoardId = "non_existing_id";

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            controller.getBoard(invalidBoardId);
        });
        assertEquals(HttpStatus.NOT_FOUND, ((ResponseStatusException) exception).getStatusCode());
    }
    @Test
    public void testDeleteUser() {
        String boardId = String.valueOf(board1.getID());

        controller.deleteBoard(boardId);

        assertTrue(repo.findById(boardId).isEmpty());

    }
}
