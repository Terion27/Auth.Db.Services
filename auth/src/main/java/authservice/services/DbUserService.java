package authservice.services;

import authservice.interfaces.IDbUserService;
import authservice.models.dto.UserAuthDto;
import authservice.models.dto.UserRegDto;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class DbUserService implements IDbUserService {
    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    WebClient client = WebClient.builder()
            .baseUrl("http://localhost:5430/api/v1/db/user")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

    @Override
    public Mono<UserAuthDto> getUser(String username) {
        try {
            return client.get()
                    .uri("/get?username={username}", username)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                    .bodyToMono(UserAuthDto.class);
        } catch (Exception e) {
            System.out.println("Ошибка GET to DB" + e.getMessage());
        }
        return Mono.empty();
    }

    @Override
    public Mono<UserAuthDto> addUser(UserRegDto userRegDto) {
        try {
            return client.post()
                    .uri("/reg")
                    .bodyValue(userRegDto)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                    .bodyToMono(UserAuthDto.class);
        } catch (Exception e) {
            System.out.println("Ошибка POST to DB" + e.getMessage());
        }
        return Mono.empty();
    }

    @Override
    public Mono<UserAuthDto> updateUser(UserAuthDto userAuthDto) {
        try {
            return client.post()
                    .uri("/update")
                    .bodyValue(userAuthDto)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                    .bodyToMono(UserAuthDto.class);
        } catch (Exception e) {
            System.out.println("Ошибка UPDATE to DB" + e.getMessage());
        }
        return Mono.empty();
    }
}
