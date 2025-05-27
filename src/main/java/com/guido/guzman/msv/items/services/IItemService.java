package com.guido.guzman.msv.items.services;

import com.guido.guzman.msv.items.models.Item;

import java.util.List;
import java.util.Optional;

public interface IItemService {
    List<Item> findAll();
    Optional<Item> findById(Long id);
}
