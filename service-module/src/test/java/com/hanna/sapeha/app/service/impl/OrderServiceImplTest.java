package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.OrderRepository;
import com.hanna.sapeha.app.repository.model.Order;
import com.hanna.sapeha.app.service.converter.OrderConverter;
import com.hanna.sapeha.app.service.model.OrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderConverter orderConverter;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldGetEmptyList() {
        List<OrderDTO> orders = orderService.getOrders();
        assertTrue(orders.isEmpty());
    }

    @Test
    void shouldGetOrdersList() {
        Order order = new Order();
        long id = 1L;
        order.setId(id);

        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        List<OrderDTO> orderDTOS = Collections.singletonList(orderDTO);

        when(orderConverter.convert(orders)).thenReturn(orderDTOS);
        List<OrderDTO> result = orderService.getOrders();

        assertEquals(id, result.get(0).getId());
    }
}