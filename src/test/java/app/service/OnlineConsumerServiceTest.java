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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@DisplayName("OnlineConsumerServiceTest")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnlineConsumerServiceTest {

    PaymentDTO paymentDTO = new PaymentDTO();
    @BeforeEach
    void setUp() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId("payment-id");
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
    OnlineConsumerService onlineConsumerService;

    @Test
    @DisplayName("onlineService_shouldSavePayment_whenParamsValid")
    void onlineService_shouldSavePayment_whenParamsValid() {
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(new Account()));
        Mockito.when(externalApiGateway.isValidPayment(Mockito.any())).thenReturn(true);

        onlineConsumerService.paymentSaver(paymentDTO);

        Mockito.verify(paymentRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("onlineService_shouldSendErrorLog_whenMathcingAccountCouldNotFound")
    void onlineService_shouldSendErrorLog_whenMathcingAccountCouldNotFound() {
        Mockito.when(externalApiGateway.isValidPayment(Mockito.any())).thenReturn(true);
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        onlineConsumerService.paymentSaver(paymentDTO);

        Mockito.verify(externalApiGateway, Mockito.times(1)).sendErrorLog2Server(Mockito.any());
    }

    @Test
    @DisplayName("onlineService_shouldSendErrorLog_whenThereIsExceptionOccur")
    void onlineService_shouldSendErrorLog_whenThereIsExceptionOccur() {
        Mockito.when(externalApiGateway.isValidPayment(Mockito.any())).thenReturn(true);
        Mockito.when(accountRepository.findById(Mockito.any())).thenThrow(new NullPointerException());

        onlineConsumerService.paymentSaver(paymentDTO);

        Mockito.verify(externalApiGateway, Mockito.times(1)).sendErrorLog2Server(Mockito.any());
    }

    @Test
    @DisplayName("onlineService_shouldSendErrorLog_whenPaymentCantValidated")
    void onlineService_shouldSendErrorLog_whenPaymentCantValidated() {
        Mockito.when(externalApiGateway.isValidPayment(Mockito.any())).thenReturn(false);

        onlineConsumerService.paymentSaver(paymentDTO);

        Mockito.verify(externalApiGateway, Mockito.times(1)).sendErrorLog2Server(Mockito.any());
    }
}