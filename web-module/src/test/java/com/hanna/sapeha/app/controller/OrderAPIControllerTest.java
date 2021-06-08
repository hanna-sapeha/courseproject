package com.hanna.sapeha.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanna.sapeha.app.service.OrderService;
import com.hanna.sapeha.app.service.model.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ORDERS_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderAPIController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@ActiveProfiles("test")
public class OrderAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetOkStatusWhenGetOrders() throws Exception {
        mockMvc.perform(
                get(API_URL + ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldGetEmptyOrdersListWhenGetOrders() throws Exception {

        MvcResult result = mockMvc.perform(
                get(API_URL + ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void shouldGetListOfOrdersWhenGetOrders() throws Exception {
        OrderDTO order = new OrderDTO();
        long id = 1L;
        order.setId(id);
        Long numberOfOrder = 1L;
//        order.setNumberOfOrder(numberOfOrder);

        when(orderService.getOrders()).thenReturn(Collections.singletonList(order));
        MvcResult result = mockMvc.perform(
                get(API_URL + ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(Collections.singletonList(order)));
    }
}
