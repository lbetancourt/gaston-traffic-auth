package co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence.repository;

import co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence.entity.AccessDetailEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccessDetailRepository extends R2dbcRepository<AccessDetailEntity, Integer> {
    Flux<AccessDetailEntity> findByAccessCode(String accessCode);
}
