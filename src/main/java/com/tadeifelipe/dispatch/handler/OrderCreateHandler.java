package com.tadeifelipe.dispatch.handler;

import com.tadeifelipe.dispatch.message.OrderCreated;
import com.tadeifelipe.dispatch.service.DispatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCreateHandler {

    private final DispatchService dispatchService;

    @KafkaListener(
            id = "orderConsumerCliente",
            topics = "order.created",
            groupId = "dispatch.order.created.consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderCreated paylod) {
        log.info("Received message: payload " + paylod);

        try {
            dispatchService.process(paylod);
        } catch (Exception e) {
            log.error("Processing failure ", e);
        }
    }
}
