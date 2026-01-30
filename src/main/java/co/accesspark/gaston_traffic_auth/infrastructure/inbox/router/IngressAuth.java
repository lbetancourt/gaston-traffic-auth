package co.accesspark.gaston_traffic_auth.infrastructure.inbox.router;

import co.accesspark.gaston_traffic_auth.infrastructure.inbox.router.handler.AuthorizationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class IngressAuth {

    @Bean
    public RouterFunction<ServerResponse> ingressRoutes(AuthorizationHandler authorizationHandler) {
        return RouterFunctions.route()
                .POST("/auth/**", authorizationHandler::auth)
                .build();
    }
}
