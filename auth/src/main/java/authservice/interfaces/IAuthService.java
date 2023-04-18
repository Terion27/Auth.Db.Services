package authservice.interfaces;

import authservice.models.dto.UserLoginDto;
import authservice.models.dto.UserRegDto;
import authservice.models.dto.ValidToken;
import authservice.models.req_resp.AuthReqResp;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IAuthService {
    Mono<ResponseEntity<AuthReqResp<String>>> reg(UserRegDto userReg);

    Mono<ResponseEntity<AuthReqResp<String>>> login(UserLoginDto userLogin);

    Mono<ResponseEntity<Boolean>> valid(ValidToken validToken);

    Mono<ResponseEntity<Boolean>> logout(ValidToken validToken);
}
