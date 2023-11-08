package com.tadeifelipe.dispatch.handler;

import com.tadeifelipe.dispatch.message.OrderCreated;
import com.tadeifelipe.dispatch.service.DispatchService;
import com.tadeifelipe.dispatch.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderCreateHandlerTest {

    private OrderCreateHandler handler;

    private DispatchService dispatchServiceMock;

    @BeforeEach
    void setup() {
        dispatchServiceMock = mock(DispatchService.class);

        handler = new OrderCreateHandler(dispatchServiceMock);
    }

    @Test
    void listen() throws Exception {
        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(randomUUID(), randomUUID().toString());
        handler.listen(testEvent);

        verify(dispatchServiceMock, times(1)).process(testEvent);
    }

    @Test
    void listenShouldThrowsException() throws Exception {
        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(randomUUID(), randomUUID().toString());
        doThrow(new RuntimeException("Service failure")).when(dispatchServiceMock).process(testEvent);

        handler.listen(testEvent);

        verify(dispatchServiceMock, times(1)).process(testEvent);
    }
}