package co.accesspark.gaston_traffic_auth.domain.service;

import co.accesspark.gaston_traffic_auth.application.AccessDetailRepositoryPort;
import co.accesspark.gaston_traffic_auth.application.AccessDetailUseCase;
import co.accesspark.gaston_traffic_auth.domain.model.AccessDetail;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AccessDetailService implements AccessDetailUseCase {

    private final AccessDetailRepositoryPort accessDetailRepositoryPort;

    public AccessDetailService(AccessDetailRepositoryPort accessDetailRepositoryPort) {
        this.accessDetailRepositoryPort = accessDetailRepositoryPort;
    }

    @Override
    public Mono<String> findCompaniesByAccessCode(String accessCode) {
        return accessDetailRepositoryPort.findByAccessCode(accessCode)
                .map(AccessDetail::getCompany)
                .distinct()
                .collectList()
                .map(list ->
                        String.join(",", list.stream().map(String::valueOf).toList()))
                .map(listCompaniesRaw ->
                        Base64.getEncoder().encodeToString(listCompaniesRaw.getBytes(StandardCharsets.UTF_8)));
    }
}
