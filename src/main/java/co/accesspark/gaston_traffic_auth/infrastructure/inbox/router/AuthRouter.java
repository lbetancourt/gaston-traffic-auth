package co.accesspark.gaston_traffic_auth.infrastructure.inbox.router;

import co.accesspark.gaston_traffic_auth.infrastructure.inbox.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouter {
    @Bean
    public RouterFunction<ServerResponse> authRoutes(AuthHandler handler) {
        return route()
                .route(path("/auth").or(path("/auth/**")), handler::auth)
                .build();
    }
}
