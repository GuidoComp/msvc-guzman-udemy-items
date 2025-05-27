package com.guido.guzman.msv.items.controllers;

import com.guido.guzman.msv.items.models.Item;
import com.guido.guzman.msv.items.services.IItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {
    private final IItemService itemService;

    public ItemController(@Qualifier("itemServiceWebClient") IItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> list() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Product not found on products microservice"));
    }
}
