package app.mapper;

import app.dto.PaymentDTO;
import app.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper PAYMENT_MAPPER_INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment paymentDTO2Payment(PaymentDTO paymentDTO);

    PaymentDTO payment2PaymentDTO(Payment payment);


}
