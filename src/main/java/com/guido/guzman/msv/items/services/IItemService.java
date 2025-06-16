package com.guido.guzman.msv.items.services;

import com.guidio.guzman.libs.msv.commons.entities.Product;
import com.guido.guzman.msv.items.models.Item;

import java.util.List;
import java.util.Optional;

public interface IItemService {
    List<Item> findAll();
    Optional<Item> findById(Long id);
    Product save(Product productDTO);
    Product update(Product productDTO, Long id);
    void deleteById(Long id);
}
