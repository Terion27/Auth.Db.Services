package dbservice.repositories;

import dbservice.models.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserAuthRepository extends R2dbcRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
