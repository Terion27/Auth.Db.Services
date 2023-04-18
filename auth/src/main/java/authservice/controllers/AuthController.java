package authservice.controllers;

import authservice.models.dto.UserLoginDto;
import authservice.models.dto.UserRegDto;
import authservice.models.dto.ValidToken;
import authservice.models.req_resp.AuthReqResp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import authservice.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/reg")
    public Mono<ResponseEntity<AuthReqResp<String>>> reg(@RequestBody UserRegDto userReg) {
        return authService.reg(userReg);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthReqResp<String>>> login(@RequestBody UserLoginDto userLogin) {
        return authService.login(userLogin);
    }

    @PostMapping("/valid")
    public Mono<ResponseEntity<Boolean>> valid(@RequestBody ValidToken validToken) {
        return authService.valid(validToken);
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity<Boolean>> logout(@RequestBody ValidToken validToken) {
        return authService.logout(validToken);
    }

}