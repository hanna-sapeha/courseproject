package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.ItemRepository;
import com.hanna.sapeha.app.repository.OrderRepository;
import com.hanna.sapeha.app.repository.UserRepository;
import com.hanna.sapeha.app.repository.model.Item;
import com.hanna.sapeha.app.repository.model.Order;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.repository.model.enums.StatusEnum;
import com.hanna.sapeha.app.service.OrderService;
import com.hanna.sapeha.app.service.converter.OrderConverter;
import com.hanna.sapeha.app.service.model.OrderDTO;
import com.hanna.sapeha.app.service.model.OrderFormDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;


    @Override
    @Transactional
    public List<OrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderConverter.convert(orders);
    }

    @Override
    @Transactional
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id);
        return orderConverter.convert(order);

    }

    @Override
    @Transactional
    public PageDTO<OrderDTO> getAllByPagination(Integer pageNumber, Integer pageSize) {
        PageDTO<OrderDTO> page = new PageDTO<>();
        List<Order> reviews = orderRepository.findAll(pageNumber, pageSize);
        List<OrderDTO> reviewDTOs = orderConverter.convert(reviews);
        page.getObjects().addAll(reviewDTOs);
        Long countReviews = orderRepository.getCount();
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, ServiceUtil.getNumbersOfPages(pageSize, countReviews))
                .boxed()
                .collect(Collectors.toList());
        page.getNumbersOfPage().addAll(numbersOfPages);
        return page;
    }

    @Override
    @Transactional
    public OrderDTO add(OrderFormDTO orderDTO, String authorizedUserEmail) {
        Order order = createOrder(orderDTO, authorizedUserEmail);
        orderRepository.persist(order);
        return orderConverter.convert(order);
    }

    @Override
    @Transactional
    public OrderDTO getOrderByNumber(String numberOfOrder) {
        Order order = orderRepository.findByNumber(numberOfOrder);
        return orderConverter.convert(order);
    }

    @Override
    @Transactional
    public OrderDTO changeStatus(String numberOfOrder, StatusEnum statusName) {
        Order order = orderRepository.findByNumber(numberOfOrder);
        order.setStatus(statusName);
        return orderConverter.convert(order);
    }

    @Override
    @Transactional
    public PageDTO<OrderDTO> getAllForUserByPagination(int pageNumber, int pageSize, Long idUser) {
        PageDTO<OrderDTO> page = new PageDTO<>();
        List<Order> reviews = orderRepository.findAllForUser(pageNumber, pageSize, idUser);
        List<OrderDTO> reviewDTOs = orderConverter.convert(reviews);
        page.getObjects().addAll(reviewDTOs);
        Long countReviews = orderRepository.getCount();
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, ServiceUtil.getNumbersOfPages(pageSize, countReviews))
                .boxed()
                .collect(Collectors.toList());
        page.getNumbersOfPage().addAll(numbersOfPages);
        return page;
    }

    private Order createOrder(OrderFormDTO orderDTO, String authorizedUserEmail) {
        Order order = orderConverter.convert(orderDTO);
        User user = userRepository.getUserByEmail(authorizedUserEmail);
        order.setUser(user);
        Item item = itemRepository.findById(orderDTO.getItemId());
        order.setItem(item);
        order.setStatus(StatusEnum.NEW);
        var finalPrice = getFinalPrice(order);
        order.setFinalPrice(finalPrice);
        LocalDate date = LocalDate.now();
        order.setDate(date);
        order.setNumberOfOrder(String.valueOf(System.currentTimeMillis()));
        return order;
    }

    private BigDecimal getFinalPrice(Order order) {
        BigDecimal itemPrice = order.getItem().getPrice();
        Integer countOfItems = order.getCountOfItems();
        return itemPrice.multiply(BigDecimal.valueOf(countOfItems));
    }
}
