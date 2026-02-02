package co.accesspark.gaston_traffic_auth.application;

import co.accesspark.gaston_traffic_auth.domain.model.AccessDetail;
import reactor.core.publisher.Flux;

public interface AccessDetailRepositoryPort {
    Flux<AccessDetail> findByAccessCode(String accessCode);
}
