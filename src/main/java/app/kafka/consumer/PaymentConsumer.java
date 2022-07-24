package app.kafka.consumer;


import app.dto.PaymentDTO;
import app.kafka.KafkaConstants;
import app.service.OfflineConsumerService;
import app.service.OnlineConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumer {

    @Autowired
    OfflineConsumerService offlineConsumerService;

    @Autowired
    OnlineConsumerService onlineConsumerService;

    @KafkaListener(topics = KafkaConstants.OFFLINE_TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void offlineConsumer(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = objectMapper.readValue(message, PaymentDTO.class);
        offlineConsumerService.savePayment(paymentDTO);
    }

    @KafkaListener(topics = KafkaConstants.ONLINE_TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void onlineConsumer(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = objectMapper.readValue(message, PaymentDTO.class);
        onlineConsumerService.savePayment(paymentDTO);
    }
}
