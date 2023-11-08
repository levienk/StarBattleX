package starb.server.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import starb.domain.json.User;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'_id' : ?0}")
    User updateField(String id, User newValue);
}
