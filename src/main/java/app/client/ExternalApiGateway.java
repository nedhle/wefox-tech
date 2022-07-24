package app.client;

import app.dto.PaymentDTO;
import app.payload.PaymentErrorLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExternalApiGateway {

    private String url;

    private String logPath;

    private String paymentPath;

    private final WebClient webClient;

    @Autowired
    public ExternalApiGateway(@Value("${external.api.gateway.url}") String url,
                              @Value("${external.api.gateway.path.log}") String logPath,
                              @Value("${external.api.gateway.path.payment}") String paymentPath) {
        this.url = url;
        this.logPath = logPath;
        this.paymentPath = paymentPath;
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void sendErrorLog2Server(PaymentErrorLogRequest errorLogDto) {
        webClient.post()
                .uri(logPath)
                .body(Mono.just(errorLogDto), PaymentErrorLogRequest.class)
                .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode()))
                .block();
    }

    public boolean isValidPayment(PaymentDTO paymentDTO) {
        HttpStatus httpStatus = webClient.post()
                .uri(paymentPath)
                .body(Mono.just(paymentDTO), PaymentDTO.class)
                .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode()))
                .block();

        if (httpStatus != null && httpStatus.is2xxSuccessful()) return true;
        return false;

    }
}
