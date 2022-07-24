package app.service;

import app.client.ExternalApiGateway;
import app.dto.PaymentDTO;
import app.model.Account;
import app.repository.AccountRepository;
import app.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("OfflineConsumerServiceTest")
class OfflineConsumerServiceTest {

    PaymentDTO paymentDTO = new PaymentDTO();
    @BeforeEach
    void setUp() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId("payment-id-example");
        paymentDTO.setPaymentType("offline");
        paymentDTO.setAccountId(123L);
        paymentDTO.setAmount(1L);
        paymentDTO.setDelay(12L);
        paymentDTO.setCreditCard("");
   }

    @Mock
    ExternalApiGateway externalApiGateway;

    @Mock
    AccountRepository accountRepository;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    OfflineConsumerService offlineConsumerService;

    @Test
    @DisplayName("offlineConsumerService_shouldSavePayment_whenParamsValid")
    void offlineConsumerService_shouldSavePayment_whenParamsValid() {
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(new Account()));

        offlineConsumerService.paymentSaver(paymentDTO);

        verify(paymentRepository, times(1)).save(Mockito.any());

    }

    @Test
    @DisplayName("offlineConsumerService_shouldSendErrorLog_whenMathcingAccountCouldNotFound")
    void offlineConsumerService_shouldSendErrorLog_whenMathcingAccountCouldNotFound() {
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        offlineConsumerService.paymentSaver(paymentDTO);

        Mockito.verify(externalApiGateway, Mockito.times(1))
                .sendErrorLog2Server(Mockito.any());
    }
}