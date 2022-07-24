package app.mapper;

import app.dto.PaymentDTO;
import app.model.Payment;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-24T22:03:03+0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_322 (Amazon.com Inc.)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment paymentDTO2Payment(PaymentDTO paymentDTO) {
        if ( paymentDTO == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setPaymentId( paymentDTO.getPaymentId() );
        payment.setAccountId( paymentDTO.getAccountId() );
        payment.setPaymentType( paymentDTO.getPaymentType() );
        payment.setCreditCard( paymentDTO.getCreditCard() );
        payment.setAmount( paymentDTO.getAmount() );

        return payment;
    }

    @Override
    public PaymentDTO payment2PaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setPaymentId( payment.getPaymentId() );
        paymentDTO.setAccountId( payment.getAccountId() );
        paymentDTO.setPaymentType( payment.getPaymentType() );
        paymentDTO.setCreditCard( payment.getCreditCard() );
        paymentDTO.setAmount( payment.getAmount() );

        return paymentDTO;
    }
}
