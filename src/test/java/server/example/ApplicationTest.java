package server.example;

import io.micronaut.configuration.metrics.management.endpoint.MetricsEndpoint.AvailableTag;
import io.micronaut.configuration.metrics.management.endpoint.MetricsEndpoint.MetricDetails;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MicronautTest
public class ApplicationTest {
    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    public void clientErrorsShouldNotBeReportedAsServerError() {

        HttpStatus expectedResponse = HttpStatus.UNAUTHORIZED;

        assertThatThrownBy(() -> client.toBlocking().exchange(HttpRequest.GET("/this-route-does-not-exist")))
            .isInstanceOfSatisfying(
                HttpClientResponseException.class,
                resp -> assertThat(resp.getStatus().getCode()).isEqualTo(expectedResponse.getCode())
            );

        MetricDetails metricDetails =
            client.toBlocking().retrieve(HttpRequest.GET("/metrics/http.server.requests"), MetricDetails.class);

        final AvailableTag statusTag = metricDetails.getAvailableTags().stream()
                                           .filter(tag -> "status".equals(tag.getTag()))
                                           .findFirst()
                                           .orElseThrow();

        assertThat(statusTag
                       .getValues()
                       .contains(expectedResponse)).isTrue();

        assertThat(statusTag
                       .getValues()
                       .contains("500")).isFalse();



    }

}
