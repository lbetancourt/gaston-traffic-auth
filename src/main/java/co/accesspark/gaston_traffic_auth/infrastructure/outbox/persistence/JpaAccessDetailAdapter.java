package co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence;

import co.accesspark.gaston_traffic_auth.application.AccessDetailRepositoryPort;
import co.accesspark.gaston_traffic_auth.domain.model.AccessDetail;
import co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence.mapper.AccessDetailMapper;
import co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence.repository.AccessDetailRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class JpaAccessDetailAdapter implements AccessDetailRepositoryPort {

    private final AccessDetailRepository accessDetailRepository;
    private final AccessDetailMapper accessDetailMapper;

    public JpaAccessDetailAdapter(AccessDetailRepository accessDetailRepository, AccessDetailMapper accessDetailMapper) {
        this.accessDetailRepository = accessDetailRepository;
        this.accessDetailMapper = accessDetailMapper;
    }

    @Override
    public Flux<AccessDetail> findByAccessCode(String accessCode) {
        return accessDetailRepository.findByAccessCode(accessCode)
                .map(accessDetailMapper::toDomain);
    }
}
