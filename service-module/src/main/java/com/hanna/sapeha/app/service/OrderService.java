package com.hanna.sapeha.app.service;

import com.hanna.sapeha.app.repository.model.enums.StatusEnum;
import com.hanna.sapeha.app.service.model.OrderDTO;
import com.hanna.sapeha.app.service.model.OrderFormDTO;
import com.hanna.sapeha.app.service.model.PageDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getOrders();

    OrderDTO getOrder(Long id);

    PageDTO<OrderDTO> getAllByPagination(Integer pageNumber, Integer pageSize);

    OrderDTO add(OrderFormDTO order, String authorizedUserEmail);

    OrderDTO getOrderByNumber(String numberOfOrder);

    OrderDTO changeStatus(String numberOfOrder, StatusEnum statusName);

    PageDTO<OrderDTO> getAllForUserByPagination(int pageNumber, int pageSize, Long idUser);
}
