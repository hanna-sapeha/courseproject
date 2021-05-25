package com.hanna.sapeha.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanna.sapeha.app.controller.model.ErrorDTO;
import com.hanna.sapeha.app.service.ItemService;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.ItemFormDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ITEMS_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemAPIController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@ActiveProfiles("test")
class ItemAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemService itemService;

    @Test
    void shouldGetEmptyListOfItems() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatBusinessLogicWasCalledWhenGetItems() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(itemService, times(1)).getItems();
    }

    @Test
    void shouldReturnEmptyCollectionWhenGetItems() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void shouldReturnCollectionsOfObjectsWhenGetItems() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(1L);
        itemDTO.setName("Name of item");
        List<ItemDTO> items = Collections.singletonList(itemDTO);

        when(itemService.getItems()).thenReturn(items);

        MvcResult mvcResult = mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringCase(objectMapper.writeValueAsString(items));
    }

    @Test
    void shouldNoCreateEmptyItem() throws Exception {
        ItemFormDTO itemDTO = new ItemFormDTO();
        mockMvc.perform(
                post(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldAddValidItemAndExpectCreated() throws Exception {
        ItemFormDTO itemForm = new ItemFormDTO();
        itemForm.setName("Name of item");
        itemForm.setPrice(BigDecimal.valueOf(2.50));
        itemForm.setSummary("test summary");
        mockMvc.perform(
                post(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemForm))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldGetItemAndReturnStatusOk() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        long id = 1L;
        itemDTO.setId(id);
        itemDTO.setName("Name of item");

        when(itemService.getItem(id)).thenReturn(itemDTO);

        MvcResult mvcResult = mockMvc.perform(
                get(API_URL + ITEMS_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringCase(objectMapper.writeValueAsString(itemDTO));
    }

    @Test
    void shouldAddNotValidNameItem() throws Exception {
        ItemFormDTO itemForm = new ItemFormDTO();
        itemForm.setName("s");
        itemForm.setPrice(BigDecimal.valueOf(3.4));
        itemForm.setSummary("summary");

        MvcResult result = mockMvc.perform(
                post(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemForm))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorMessage = "characters count should be in the range between 2 and 40";
        error.add(errorMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }

    @Test
    void shouldAddNotValidSummaryItem() throws Exception {
        ItemFormDTO itemForm = new ItemFormDTO();
        itemForm.setName("name");
        itemForm.setPrice(BigDecimal.valueOf(3.4));
        itemForm.setSummary("s");

        MvcResult result = mockMvc.perform(
                post(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemForm))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorMessage = "characters count should be in the range between 2 and 200";
        error.add(errorMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }

    @Test
    void shouldAddNotValidPriceItem() throws Exception {
        ItemFormDTO itemForm = new ItemFormDTO();
        itemForm.setName("name");
        itemForm.setPrice(BigDecimal.valueOf(0));
        itemForm.setSummary("summary");

        MvcResult result = mockMvc.perform(
                post(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemForm))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorMessage = "must be greater than or equal to 0.1";
        error.add(errorMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }
}