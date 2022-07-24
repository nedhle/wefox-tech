package app.service;

import app.client.ExternalApiGateway;
import app.dto.PaymentDTO;
import app.enums.PaymentErrorEnum;
import app.payload.PaymentErrorLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineConsumerService extends AbstractPaymentService {

    @Autowired
    ExternalApiGateway externalApiGateway;

    public void savePayment(PaymentDTO paymentDTO) {
        if (externalApiGateway.isValidPayment(paymentDTO)) {
            try {
                paymentSaver(paymentDTO);
            } catch (Exception e) {
                externalApiGateway.sendErrorLog2Server(PaymentErrorLogRequest.builder()
                        .paymentId(paymentDTO.getPaymentId())
                        .errorType(PaymentErrorEnum.INTERNAL.name())
                        .errorDescription(PaymentErrorEnum.INTERNAL.getMessage())
                        .build());
            }
        } else {
            externalApiGateway.sendErrorLog2Server(PaymentErrorLogRequest.builder()
                    .paymentId(paymentDTO.getPaymentId())
                    .errorType(PaymentErrorEnum.INVALID_PAYMENT.name())
                    .errorDescription(PaymentErrorEnum.INVALID_PAYMENT.getMessage())
                    .build());
        }
    }
}
