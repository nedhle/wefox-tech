package app.service;

import app.client.ExternalApiGateway;
import app.dto.PaymentDTO;
import app.enums.PaymentErrorEnum;
import app.payload.PaymentErrorLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfflineConsumerService extends AbstractPaymentService {

    @Autowired
    ExternalApiGateway externalApiGateway;

    public void savePayment(PaymentDTO paymentDTO) {
        try {
            paymentSaver(paymentDTO);
        } catch (Exception e) {
            externalApiGateway.sendErrorLog2Server(PaymentErrorLogRequest.builder()
                    .paymentId(paymentDTO.getPaymentId())
                    .errorType(PaymentErrorEnum.INTERNAL.name())
                    .errorDescription(PaymentErrorEnum.INTERNAL.getMessage())
                    .build());
        }
    }
}
