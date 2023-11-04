package starb.server.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import starb.domain.game.Square;

public interface userRepository extends MongoRepository<Square, String> {
}
