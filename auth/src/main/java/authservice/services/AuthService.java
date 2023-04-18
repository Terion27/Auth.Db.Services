package authservice.services;

import authservice.interfaces.IAuthService;
import authservice.models.dto.UserAuthDto;
import authservice.models.dto.UserLoginDto;
import authservice.models.dto.UserRegDto;
import authservice.models.dto.ValidToken;
import authservice.models.req_resp.AuthReqResp;
import authservice.utils.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
public class AuthService implements IAuthService {

    private final DbUserService dbUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(DbUserService dbUserService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.dbUserService = dbUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Mono<ResponseEntity<AuthReqResp<String>>> reg(UserRegDto userReg) {
        if (!(userReg.getUsername().isEmpty() || userReg.getPassword().isEmpty())) {
            return dbUserService.getUser(userReg.getUsername().toLowerCase())
                    .map(u -> ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                            .body(new AuthReqResp<>("", "Login is incorrect")))
                    .switchIfEmpty(dbUserService.addUser(new UserRegDto(
                                    userReg.getUsername(),
                                    passwordEncoder.encode(userReg.getPassword())))
                            .map(u -> ResponseEntity.status(HttpStatus.CREATED)
                                    .body(new AuthReqResp<>("OK", "User " + userReg.getUsername() + " is registered")))
                            .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body(new AuthReqResp<>("", "BAD_REQUEST"))));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthReqResp<>("", "Fields must not be empty")));
    }

    @Override
    public Mono<ResponseEntity<AuthReqResp<String>>> login(UserLoginDto userLogin) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (!(userLogin.getPassword().isEmpty() || userLogin.getUsername().isEmpty())) {
            return dbUserService.getUser(userLogin.getUsername().toLowerCase())
                    .filter(u -> u.getUsername().equals(userLogin.getUsername()) &&
                            passwordEncoder.matches(userLogin.getPassword(), u.getPassword()))
                    .filter(UserAuthDto::isStatus)
                    .flatMap(u -> {
                        u.setCreateTokenDateTime(localDateTime);
                        return dbUserService.updateUser(u)
                                .flatMap(userUpdate -> Mono.just(ResponseEntity.ok()
                                        .header(HttpHeaders.AUTHORIZATION,
                                                jwtService.generate(userUpdate.getUsername(), localDateTime))
                                        .body(new AuthReqResp<>("", "Success"))))
                                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new AuthReqResp<>("", "Update: Credentials are incorrect"))));
                    })
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new AuthReqResp<>("", "Credentials are incorrect")))
                    .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new AuthReqResp<>("", "Credentials are incorrect"))));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthReqResp<>("", "Fields must not be empty")));
    }

    @Override
    public Mono<ResponseEntity<Boolean>> valid(ValidToken validToken) {
        return dbUserService.getUser(validToken.getUsername().toLowerCase())
                .filter(UserAuthDto::isStatus)
                .flatMap(u -> {
                    if (jwtService.isValid(validToken.getToken(), validToken.getUsername(),
                            u.getCreateTokenDateTime())) {
                        return Mono.just(ResponseEntity.ok().body(true));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false)));
    }

    @Override
    public Mono<ResponseEntity<Boolean>> logout(ValidToken validToken) {
        return dbUserService.getUser(validToken.getUsername().toLowerCase())
                .filter(UserAuthDto::isStatus)
                .flatMap(u -> {
                    if (jwtService.isValid(validToken.getToken(), validToken.getUsername(),
                            u.getCreateTokenDateTime())) {
                        u.setCreateTokenDateTime(null);
                        return dbUserService.updateUser(u)
                                .flatMap(userUpdate -> Mono.just(ResponseEntity.ok()
                                        .body(true)))
                                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false))
                                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false)));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false)));
    }

}
