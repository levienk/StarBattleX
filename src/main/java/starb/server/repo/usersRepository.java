package starb.server.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface usersRepository extends MongoRepository<User, IdDataType> {
}
