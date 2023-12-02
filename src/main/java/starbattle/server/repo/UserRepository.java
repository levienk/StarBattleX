package starbattle.server.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import starbattle.domain.user.User;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'_id' : ?0}")
    User updateField(String id, User newValue);
}
