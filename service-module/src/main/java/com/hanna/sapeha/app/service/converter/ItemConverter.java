package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Item;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.ItemFormDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemConverter {

    public ItemDTO convertEntityToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setUniqueNumber(item.getUniqueNumber());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setSummary(item.getSummary());
        return itemDTO;
    }

    public List<ItemDTO> convertEntitiesToDTO(List<Item> items) {
        return items.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public Item convertDTOToEntity(ItemFormDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setSummary(itemDTO.getSummary());
        return item;
    }
}
