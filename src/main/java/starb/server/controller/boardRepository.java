package starb.server.controller;

import org.springframework.data.mongodb.repository.MongoRepository;
import starb.domain.game.Board;

public interface boardRepository extends MongoRepository<Board, String> {
}
