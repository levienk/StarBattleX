package starb.server.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import starb.domain.game.Board;

import java.time.LocalDate;
import java.util.List;

public interface boardsRepository extends MongoRepository<Board, String>{

}
