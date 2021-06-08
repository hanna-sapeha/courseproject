package com.hanna.sapeha.app.repository;

import com.hanna.sapeha.app.repository.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends GenericRepository<Long, Item> {

    List<Item> findAll(int pageNumber, int pageSize);

    Item copy(Item item);

    Item findByUuid(UUID uuid);
}
