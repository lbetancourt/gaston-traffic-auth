package co.accesspark.gaston_traffic_auth.infrastructure.inbox.handler;

import co.accesspark.gaston_traffic_auth.application.AccessDetailUseCase;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

    private final AccessDetailUseCase accessDetailUseCase;

    public AuthHandler(AccessDetailUseCase accessDetailUseCase) {
        this.accessDetailUseCase = accessDetailUseCase;
    }

    public Mono<ServerResponse> auth(ServerRequest request) {
        String authHeader = request.headers().firstHeader("Authorization");
        System.out.println("authHeader: " + authHeader);
        if (isValid(authHeader)) {
            return accessDetailUseCase
                    .findCompaniesByAccessCode(getStringClaimFrom(authHeader))
                    .flatMap(companies -> ServerResponse.ok().header("x-user-companies", companies).build());
        } else {
            return ServerResponse.status(401).build();
        }
    }

    private boolean isValid(String token) {
        return token != null && token.startsWith("Bearer ");
    }

    private String getStringClaimFrom(String tokenRaw) {
        try {
            String token = tokenRaw.replace("Bearer ", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return claims.getLongClaim("id").toString();
        }catch (Exception e) {
            return "";
        }
    }
}
