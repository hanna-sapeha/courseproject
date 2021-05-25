package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Item;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.ItemFormDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemConverterTest {
    private final ItemConverter itemConverter = new ItemConverter();

    @Test
    void shouldConvertItemFormDtoToItemAndReturnRightName() {
        ItemFormDTO itemDTO = new ItemFormDTO();
        String name = "test name";
        itemDTO.setName(name);
        Item item = itemConverter.convertDTOToEntity(itemDTO);
        assertEquals(name, item.getName());
    }

    @Test
    void shouldConvertItemFormDtoToItemAndReturnRightPrice() {
        ItemFormDTO itemDTO = new ItemFormDTO();
        BigDecimal price = BigDecimal.valueOf(11.5);
        itemDTO.setPrice(price);
        Item item = itemConverter.convertDTOToEntity(itemDTO);
        assertEquals(price, item.getPrice());
    }

    @Test
    void shouldConvertItemFormDtoToItemAndReturnRightSummary() {
        ItemFormDTO itemDTO = new ItemFormDTO();
        String summary = "summary";
        itemDTO.setSummary(summary);
        Item item = itemConverter.convertDTOToEntity(itemDTO);
        assertEquals(summary, item.getSummary());
    }

    @Test
    void shouldConvertItemToDtoAndReturnRightId() {
        Item item = new Item();
        long id = 1L;
        item.setId(id);
        ItemDTO itemDTO = itemConverter.convertEntityToDTO(item);
        assertEquals(id, itemDTO.getId());
    }

    @Test
    void shouldConvertItemToDtoAndReturnRightName() {
        Item item = new Item();
        String name = "test name";
        item.setName(name);
        ItemDTO itemDTO = itemConverter.convertEntityToDTO(item);
        assertEquals(name, itemDTO.getName());
    }

    @Test
    void shouldConvertItemToDtoAndReturnRightUniqueNumber() {
        Item item = new Item();
        UUID uuid = UUID.randomUUID();
        item.setUniqueNumber(uuid);
        ItemDTO itemDTO = itemConverter.convertEntityToDTO(item);
        assertEquals(uuid, itemDTO.getUniqueNumber());
    }

    @Test
    void shouldConvertItemToDtoAndReturnRightPrice() {
        Item item = new Item();
        BigDecimal price = BigDecimal.valueOf(11.5);
        item.setPrice(price);
        ItemDTO itemDTO = itemConverter.convertEntityToDTO(item);
        assertEquals(price, itemDTO.getPrice());
    }

    @Test
    void shouldConvertItemToDtoAndReturnRightSummary() {
        Item item = new Item();
        String summary = "test summary";
        item.setSummary(summary);
        ItemDTO itemDTO = itemConverter.convertEntityToDTO(item);
        assertEquals(summary, itemDTO.getSummary());
    }
}