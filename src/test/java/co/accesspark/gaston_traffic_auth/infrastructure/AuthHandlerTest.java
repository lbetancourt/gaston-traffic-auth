package co.accesspark.gaston_traffic_auth.infrastructure;

import co.accesspark.gaston_traffic_auth.application.AccessDetailUseCase;
import co.accesspark.gaston_traffic_auth.infrastructure.inbox.handler.AuthHandler;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private AccessDetailUseCase accessDetailUseCase;

    private AuthHandler authHandler;

    @BeforeEach
    void setUp() {
        authHandler = new AuthHandler(accessDetailUseCase);
    }

    @Test
    void givenTokenWhenAuthThenReturnCompanies() throws Exception {
        String accessCode = "12345";
        String token = generateTestToken(12345L);
        String authHeader = "Bearer " + token;

        ServerRequest request = mock(ServerRequest.class);
        ServerRequest.Headers headers = mock(ServerRequest.Headers.class);

        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("Authorization")).thenReturn(authHeader);

        // Simulamos que el caso de uso devuelve empresas en Base64
        String base64Companies = "QXBwbGUsR29vZ2xl"; // Apple,Google
        when(accessDetailUseCase.findCompaniesByAccessCode(accessCode))
                .thenReturn(Mono.just(base64Companies));

        Mono<ServerResponse> responseMono = authHandler.auth(request);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertEquals(200, response.statusCode().value());
                    assertEquals(base64Companies, response.headers().getFirst("x-user-companies"));
                })
                .verifyComplete();
    }

    @Test
    void givenTokenNullWhenAuthThenReturnUnauthorized() {
        ServerRequest request = mock(ServerRequest.class);
        ServerRequest.Headers headers = mock(ServerRequest.Headers.class);
        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("Authorization")).thenReturn(null);

        Mono<ServerResponse> responseMono = authHandler.auth(request);

        StepVerifier.create(responseMono)
                .assertNext(response -> assertEquals(401, response.statusCode().value()))
                .verifyComplete();
    }

    @Test
    void givenMalFormedAuthorizationWhenAuthThenReturnUnauthorized() {
        // GIVEN: Un header que no empieza con Bearer
        ServerRequest request = mock(ServerRequest.class);
        ServerRequest.Headers headers = mock(ServerRequest.Headers.class);
        when(request.headers()).thenReturn(headers);
        when(headers.firstHeader("Authorization")).thenReturn("Basic invalid-token");

        // WHEN
        Mono<ServerResponse> responseMono = authHandler.auth(request);

        // THEN
        StepVerifier.create(responseMono)
                .assertNext(response -> assertEquals(401, response.statusCode().value()))
                .verifyComplete();
    }

    private String generateTestToken(Long id) throws Exception {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("id", id)
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = new MACSigner("a".repeat(32)); // Clave dummy de 32 bytes
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}
