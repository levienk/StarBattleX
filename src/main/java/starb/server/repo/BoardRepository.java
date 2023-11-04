package starb.server.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import starb.domain.game.Board;

public interface BoardRepository extends MongoRepository<Board, String>{

}
