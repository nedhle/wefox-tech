package app.service;

import app.enums.PaymentErrorEnum;
import app.client.ExternalApiGateway;
import app.dto.PaymentDTO;
import app.payload.PaymentErrorLogRequest;
import app.mapper.PaymentMapper;
import app.model.Account;
import app.model.Payment;
import app.repository.AccountRepository;
import app.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
public abstract class AbstractPaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ExternalApiGateway externalApiGateway;

    protected void paymentSaver(PaymentDTO paymentDTO){
        Payment payment = PaymentMapper.PAYMENT_MAPPER_INSTANCE.paymentDTO2Payment(paymentDTO);

        Optional<Account> optionalAccount = accountRepository.findById(paymentDTO.getAccountId());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setLastPaymentDate(Timestamp.valueOf(LocalDateTime.now()));

            paymentRepository.save(payment);
            accountRepository.save(account);
        }else{
            externalApiGateway.sendErrorLog2Server(PaymentErrorLogRequest.builder()
                    .paymentId(paymentDTO.getPaymentId())
                    .errorType(PaymentErrorEnum.ACCOUNT_DOES_NOT_EXIST.name())
                    .errorDescription(PaymentErrorEnum.ACCOUNT_DOES_NOT_EXIST.getMessage())
                    .build());
        }
    }
}
