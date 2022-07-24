package app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("credit_card")
    private String creditCard;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("delay")
    private Long delay;

}
