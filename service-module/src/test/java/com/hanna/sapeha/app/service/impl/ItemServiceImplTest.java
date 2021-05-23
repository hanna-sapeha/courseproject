package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.ItemRepository;
import com.hanna.sapeha.app.repository.model.Item;
import com.hanna.sapeha.app.service.converter.ItemConverter;
import com.hanna.sapeha.app.service.exception.ServiceException;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.ItemFormDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemConverter itemConverter;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void shouldGetEmptyList() {
        List<Item> items = Collections.emptyList();
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemDTO> itemDTOS = Collections.emptyList();
        when(itemConverter.convertEntitiesToDTO(items)).thenReturn(itemDTOS);

        List<ItemDTO> itemDTOResult = itemService.getItems();
        assertEquals(itemDTOS, itemDTOResult);
    }

    @Test
    void shouldGetItems() {
        long id = 1L;
        Item item = new Item();
        item.setId(id);
        List<Item> items = Collections.singletonList(item);
        when(itemRepository.findAll()).thenReturn(items);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(id);
        List<ItemDTO> itemDTOS = Collections.singletonList(itemDTO);
        when(itemConverter.convertEntitiesToDTO(items)).thenReturn(itemDTOS);

        List<ItemDTO> itemDTOResult = itemService.getItems();
        assertEquals(id, itemDTOResult.get(0).getId());
    }

    @Test
    void shouldGetItem() {
        long id = 1L;
        Item item = new Item();
        item.setId(id);
        when(itemRepository.findById(id)).thenReturn(item);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(id);
        when(itemConverter.convertEntityToDTO(item)).thenReturn(itemDTO);

        ItemDTO result = itemService.getItem(id);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldNegativeGetItem() {
        long id = 1L;
        when(itemRepository.findById(id)).thenReturn(null);

        assertThrows(ServiceException.class, () ->
                itemService.getItem(id)
        );
    }

    @Test
    void shouldAddNewItem() {
        long id = 1L;
        String name = "test Name";

        ItemFormDTO itemForm = new ItemFormDTO();
        itemForm.setName(name);
        Item item = new Item();
        item.setName(name);

        when(itemConverter.convertDTOToEntity(itemForm)).thenReturn(item);

        ItemDTO itemDTO = new ItemDTO();
        item.setId(id);
        itemDTO.setId(id);
        UUID uuid = UUID.randomUUID();
        item.setUniqueNumber(uuid);
        itemDTO.setUniqueNumber(item.getUniqueNumber());

        when(itemConverter.convertEntityToDTO(item)).thenReturn(itemDTO);
        ItemDTO itemDTOResult = itemService.addItem(itemForm);

        assertNotNull(itemDTOResult.getUniqueNumber());
        assertEquals(id, itemDTOResult.getId());
    }

    @Test
    void shouldRemoveItem() {
        long id = 1L;
        Item item = new Item();
        item.setId(id);
        when(itemRepository.findById(id)).thenReturn(item);

        Long itemId = itemService.removeById(id);
        assertEquals(id, itemId);
    }

    @Test
    void shouldNegativeRemoveItem() {
        long id = 1L;
        when(itemRepository.findById(id)).thenReturn(null);

        assertThrows(ServiceException.class, () ->
                itemService.getItem(id)
        );
    }
}