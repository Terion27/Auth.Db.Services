package authservice.interfaces;

import authservice.models.dto.UserAuthDto;
import authservice.models.dto.UserRegDto;
import reactor.core.publisher.Mono;

public interface IDbUserService {
    Mono<UserAuthDto> getUser(String username);

    Mono<UserAuthDto> addUser(UserRegDto userRegDto);

    Mono<UserAuthDto> updateUser(UserAuthDto userAuthDto);
}
