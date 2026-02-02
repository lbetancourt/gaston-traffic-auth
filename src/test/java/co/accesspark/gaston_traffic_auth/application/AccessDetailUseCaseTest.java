package co.accesspark.gaston_traffic_auth.application;

import co.accesspark.gaston_traffic_auth.domain.model.AccessDetail;
import co.accesspark.gaston_traffic_auth.domain.service.AccessDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccessDetailUseCaseTest {
    @Mock
    private AccessDetailRepositoryPort accessDetailRepositoryPort;

    private AccessDetailUseCase accessDetailUseCase;

    @BeforeEach
    void setUp() {
        accessDetailUseCase = new AccessDetailService(accessDetailRepositoryPort);
    }

    @Test
    void givenCompaniesWhenFindCompaniesByAccessCodeThenSuccess() {
        String accessCode = "123";

        AccessDetail detail1 = new AccessDetail();
        detail1.setCompany(23);

        AccessDetail detail2 = new AccessDetail();
        detail2.setCompany(56);

        AccessDetail detail3 = new AccessDetail();
        detail3.setCompany(223);

        AccessDetail detail4 = new AccessDetail();
        detail4.setCompany(223);

        when(accessDetailRepositoryPort.findByAccessCode(Mockito.anyString()))
                .thenReturn(Flux.just(detail1, detail2, detail3, detail4));

        String expectedRaw = "23,56,223";
        String expectedBase64 = Base64.getEncoder().encodeToString(expectedRaw.getBytes(StandardCharsets.UTF_8));

        StepVerifier.create(accessDetailUseCase.findCompaniesByAccessCode(accessCode))
                .expectNext(expectedBase64)
                .verifyComplete();
    }

    @Test
    void givenEmptyCompaniesWhenFindCompaniesByAccessCodeThenEmpty() {
        String accessCode = "EMPTY-CODE";
        when(accessDetailRepositoryPort.findByAccessCode(accessCode)).thenReturn(Flux.empty());

        StepVerifier.create(accessDetailUseCase.findCompaniesByAccessCode(accessCode))
                .expectNext("")
                .verifyComplete();
    }
}
