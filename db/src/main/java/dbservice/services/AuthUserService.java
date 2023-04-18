package dbservice.services;

import dbservice.interfaces.IAuthUserService;
import dbservice.models.User;
import dbservice.models.dto.UserAuthDto;
import dbservice.models.dto.UserRegDto;
import dbservice.models.dto.mapper.UserAuthMapper;
import dbservice.repositories.UserAuthRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AuthUserService implements IAuthUserService {
    private final UserAuthRepository userAuthRepository;
    private final UserAuthMapper userAuthMapper;

    public AuthUserService(UserAuthRepository userAuthRepository, UserAuthMapper userAuthMapper) {
        this.userAuthRepository = userAuthRepository;
        this.userAuthMapper = userAuthMapper;
    }

    @Override
    public Mono<UserAuthDto> findByUsername(String username) {
        return userAuthRepository.findByUsername(username)
                .map(userAuthMapper::map);
    }

    @Override
    public Mono<UserAuthDto> saveAuthByUsername(UserRegDto userRegDto) {
        return userAuthRepository.save(new User().toBuilder()
                        .username(userRegDto.getUsername())
                        .password(userRegDto.getPassword())
                        .registrationDate(LocalDateTime.now())
                        .status(true)
                        .visibility(true)
                        .role("ROLE_USER")
                        .build()
                )
                .map(userAuthMapper::map);
    }

    @Override
    public Mono<UserAuthDto> updateCreateTokenDateTimeById(UserAuthDto userAuthDto) {
        return userAuthRepository.findById(userAuthDto.getId())
                .flatMap(user -> {
                    user.setCreateTokenDateTime(userAuthDto.getCreateTokenDateTime());
                    return userAuthRepository.save(user).map(userAuthMapper::map);
                });
    }
}
