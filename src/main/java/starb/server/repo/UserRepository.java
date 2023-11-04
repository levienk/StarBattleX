package starb.server.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import starb.domain.game.Square;

public interface UserRepository extends MongoRepository<Square, String> {
}
