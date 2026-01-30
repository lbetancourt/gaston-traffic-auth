package co.accesspark.gaston_traffic_auth.infrastructure.inbox.router.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHandler {

    public Mono<ServerResponse> auth(ServerRequest request) {
        // Envoy envía los headers originales aquí
        String authHeader = request.headers().firstHeader("authorization");
        System.out.println("authHeader: " + authHeader);

        if (isValid(authHeader)) {
            return ServerResponse.ok()
                    // Estos headers se pueden propagar al microservicio final
                    .header("x-user-id", "user-id-123")
                    .header("x-user-role", "ADMIN")
                    .build();
        } else {
            // Si devuelves 401, Envoy bloquea la petición al destino
            return ServerResponse.status(401).build();
        }
    }

    private boolean isValid(String token) {
        // Tu lógica de validación real aquí
        return token != null && token.startsWith("Bearer ");
    }

}
