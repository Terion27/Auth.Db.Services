package dbservice.controllers;

import dbservice.models.dto.UserAuthDto;
import dbservice.models.dto.UserLoginDto;
import dbservice.models.dto.UserRegDto;
import dbservice.models.req_resp.AuthReqResp;
import dbservice.services.AuthUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/db/user")
public class AuthUserController {

    private final AuthUserService authUserService;

    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @GetMapping("/get")
    public Mono<UserAuthDto> get(@RequestParam String username) {
        return authUserService.findByUsername(username);
    }

    @PostMapping("/reg")
    public Mono<UserAuthDto> reg(@RequestBody UserRegDto userReg) {
        return authUserService.saveAuthByUsername(userReg);
    }

    @PostMapping("/update")
    public Mono<UserAuthDto> update(@RequestBody UserAuthDto userAuthDto) {
        return authUserService.updateCreateTokenDateTimeById(userAuthDto);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthReqResp<String>>> login(@RequestBody UserLoginDto userLogin) {
        return null;
    }
}