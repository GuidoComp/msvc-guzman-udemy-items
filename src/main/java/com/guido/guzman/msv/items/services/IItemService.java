package com.guido.guzman.msv.items.services;

import com.guido.guzman.msv.items.models.Item;
import com.guido.guzman.msv.items.models.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface IItemService {
    List<Item> findAll();
    Optional<Item> findById(Long id);
    ProductDTO save(ProductDTO productDTO);
    ProductDTO update(ProductDTO productDTO, Long id);
    void deleteById(Long id);
}
