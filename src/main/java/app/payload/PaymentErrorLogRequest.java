package app.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
public class PaymentErrorLogRequest {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("error_type")
    private String errorType;

    @JsonProperty("error_description")
    private String errorDescription;
}
