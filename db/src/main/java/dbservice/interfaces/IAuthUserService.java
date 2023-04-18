package dbservice.interfaces;

import dbservice.models.dto.UserAuthDto;
import dbservice.models.dto.UserRegDto;
import reactor.core.publisher.Mono;

public interface IAuthUserService {
    Mono<UserAuthDto> findByUsername(String username);

    Mono<UserAuthDto> saveAuthByUsername(UserRegDto userRegDto);

    Mono<UserAuthDto> updateCreateTokenDateTimeById(UserAuthDto userAuthDto);
}
