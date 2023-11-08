package starb.server.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import starb.domain.game.Board;

import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String>{

    Optional<Board> findById(int id);
}
