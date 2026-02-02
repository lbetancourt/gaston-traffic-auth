package co.accesspark.gaston_traffic_auth.application;

import reactor.core.publisher.Mono;

public interface AccessDetailUseCase {
    Mono<String> findCompaniesByAccessCode(String accessCode);
}
