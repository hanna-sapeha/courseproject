package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.ItemRepository;
import com.hanna.sapeha.app.repository.model.Item;
import com.hanna.sapeha.app.service.ItemService;
import com.hanna.sapeha.app.service.converter.ItemConverter;
import com.hanna.sapeha.app.service.exception.ServiceException;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.ItemFormDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    @Override
    @Transactional
    public List<ItemDTO> getItems() {
        List<Item> items = itemRepository.findAll();
        return itemConverter.convertEntitiesToDTO(items);
    }

    @Override
    @Transactional
    public ItemDTO getItem(Long id) {
        Item item = itemRepository.findById(id);
        if (Objects.nonNull(item)) {
            return itemConverter.convertEntityToDTO(item);
        } else {
            throw new ServiceException("Item with id '" + id + "' does not exist");
        }
    }

    @Override
    @Transactional
    public ItemDTO addItem(ItemFormDTO itemDTO) {
        Item item = itemConverter.convertDTOToEntity(itemDTO);
        UUID uuid = UUID.randomUUID();
        item.setUniqueNumber(uuid);
        itemRepository.persist(item);
        return itemConverter.convertEntityToDTO(item);
    }

    @Override
    @Transactional
    public Long removeById(Long id) {
        Item item = itemRepository.findById(id);
        itemRepository.remove(item);
        return item.getId();
    }

    @Override
    @Transactional
    public PageDTO<ItemDTO> getAllByPagination(int pageNumber, int pageSize) {
        PageDTO<ItemDTO> page = new PageDTO<>();
        List<Item> items = itemRepository.findAll(pageNumber, pageSize);
        List<ItemDTO> itemDTOs = itemConverter.convertEntitiesToDTO(items);
        page.getObjects().addAll(itemDTOs);
        Long countUsers = itemRepository.getCount();
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, ServiceUtil.getNumbersOfPages(pageSize, countUsers))
                .boxed()
                .collect(Collectors.toList());
        page.getNumbersOfPage().addAll(numbersOfPages);
        return page;
    }

    @Override
    @Transactional
    public ItemDTO copyItem(Long id) {
        Item item = itemRepository.findById(id);
        Item copy = itemRepository.copy(item);
        return itemConverter.convertEntityToDTO(copy);
    }

    @Override
    @Transactional
    public ItemDTO getItemByUniqueNumber(UUID uuid) {
        Item item = itemRepository.findByUuid(uuid);
        return itemConverter.convertEntityToDTO(item);
    }
}
