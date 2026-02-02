package co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence.mapper;

import co.accesspark.gaston_traffic_auth.domain.model.AccessDetail;
import co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence.entity.AccessDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessDetailMapper {
    AccessDetail toDomain(AccessDetailEntity accessDetailEntity);
    AccessDetailEntity toEntity(AccessDetail accessDetail);
}
