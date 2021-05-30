package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Order;
import com.hanna.sapeha.app.repository.model.UserDetails;
import com.hanna.sapeha.app.service.model.OrderDTO;
import com.hanna.sapeha.app.service.model.OrderFormDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public OrderDTO convert(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setNumberOfOrder(order.getNumberOfOrder());
        if (Objects.nonNull(order.getItem())) {
            orderDTO.setItemName(order.getItem().getName());
        }
        orderDTO.setCountOfItems(order.getCountOfItems());
        orderDTO.setFinalPrice(order.getFinalPrice());
        orderDTO.setStatus(order.getStatus().name());
        if (Objects.nonNull(order.getUser())) {
            String firstname = order.getUser().getFirstname();
            String lastname = order.getUser().getLastname();
            orderDTO.setUserIdentifier(String.format("%s %s", firstname, lastname));
            UserDetails userDetails = order.getUser().getUserDetails();
            if (Objects.nonNull(userDetails)) {
                orderDTO.setUserPhone(order.getUser().getUserDetails().getTelephone());
            }
        }
        return orderDTO;
    }

    public List<OrderDTO> convert(List<Order> orders) {
        return orders.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public Order convert(OrderFormDTO orderDTO) {
        Order order = new Order();
        order.setCountOfItems(orderDTO.getCountOfItems());
        return order;
    }
}
