package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.controller.exception.ControllerException;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import com.hanna.sapeha.app.repository.model.enums.StatusEnum;
import com.hanna.sapeha.app.service.ItemService;
import com.hanna.sapeha.app.service.OrderService;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.OrderDTO;
import com.hanna.sapeha.app.service.model.OrderFormDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.hanna.sapeha.app.constant.ControllerConstants.DEFAULT_PAGE_SIZE;
import static com.hanna.sapeha.app.constant.ControllerConstants.PAGE_ATTRIBUTE;
import static com.hanna.sapeha.app.constant.ControllerConstants.ROLE_ATTRIBUTE;
import static com.hanna.sapeha.app.constant.HandlerConstants.ORDERS_URL;

@Controller
@RequiredArgsConstructor
@RequestMapping(ORDERS_URL)
@Log4j2
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping
    public String getAllOrders(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber) {
        String role = ControllerUtil.getAuthorizedUser().getRoleName();
        PageDTO<OrderDTO> page = getPageByUserRole(pageNumber, role);
        model.addAttribute(PAGE_ATTRIBUTE, page);
        model.addAttribute(ROLE_ATTRIBUTE, role);
        return "orders";
    }

    @GetMapping("/add")
    public String redirectToAddOrder(Model model,
                                     OrderFormDTO orderFormDTO,
                                     @RequestParam(value = "item", required = false) UUID itemUuid) {
        if (ControllerUtil.isAuthorizedAsCustomerUser()) {
            ItemDTO item = itemService.getItemByUniqueNumber(itemUuid);
            model.addAttribute("item", item);
            return "add-order";
        } else {
            throw new ControllerException(String.format("User %s has not authority to order",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }

    @PostMapping("/add")
    public String addOrder(@Valid OrderFormDTO orderFormDTO, BindingResult errors) {
        if (ControllerUtil.isAuthorizedAsCustomerUser()) {
            if (errors.hasErrors()) {
                return "add-order";
            } else {
                String authorizedUserEmail = ControllerUtil.getAuthorizedUser().getUsername();
                orderService.add(orderFormDTO, authorizedUserEmail);
                return "redirect:" + ORDERS_URL;
            }
        } else {
            throw new ControllerException(String.format("User %s has not authority to order",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }

    @GetMapping("/{numberOfOrder}")
    public String getOrder(Model model, @PathVariable String numberOfOrder) {
        OrderDTO order = orderService.getOrderByNumber(numberOfOrder);
        model.addAttribute("order", order);
        StatusEnum[] statuses = StatusEnum.values();
        model.addAttribute("statuses", statuses);
        return "order";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam(value = "order") String numberOfOrder,
                               @RequestParam(value = "statusName") StatusEnum statusName) {
        orderService.changeStatus(numberOfOrder, statusName);
        return String.format("redirect:%s/%s", ORDERS_URL, numberOfOrder);
    }

    private PageDTO<OrderDTO> getPageByUserRole(int pageNumber, String role) {
        if (role.equals(RolesEnum.SALE_USER.name())) {
            return orderService.getAllByPagination(pageNumber, DEFAULT_PAGE_SIZE);
        } else if (role.equals(RolesEnum.CUSTOMER_USER.name())) {
            Long idUser = ControllerUtil.getAuthorizedUser().getId();
            return orderService.getAllForUserByPagination(pageNumber, DEFAULT_PAGE_SIZE, idUser);
        } else {
            throw new ControllerException(String.format("User %s has not authority to order",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }
}
