package com.hanna.sapeha.app.service;

import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.ItemFormDTO;
import com.hanna.sapeha.app.service.model.PageDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ItemDTO> getItems();

    ItemDTO getItem(Long id);

    ItemDTO addItem(ItemFormDTO item);

    Long removeById(Long id);

    PageDTO<ItemDTO> getAllByPagination(int pageNumber, int pageSize);

    ItemDTO copyItem(Long id);

    ItemDTO getItemByUniqueNumber(UUID uuid);
}
