package com.guido.guzman.msv.items.controllers;

import com.guido.guzman.msv.items.models.Item;
import com.guido.guzman.msv.items.models.ProductDTO;
import com.guido.guzman.msv.items.services.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {
    private final IItemService itemService;
    private final CircuitBreakerFactory cBreakerFactory;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ItemController(@Qualifier("itemServiceWebClient") IItemService itemService, CircuitBreakerFactory cBreakerFactory) {
        this.itemService = itemService;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping
    public List<Item> list(@RequestParam(name="name", required = false) String name,
                           @RequestHeader(name="token-request", required = false) String token) {
        System.out.println("Name request: " + name);
        System.out.println("Token request: " + token);
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<Item> item = cBreakerFactory.create("items").run(() -> itemService.findById(id), error -> {
            logger.error(error.getMessage());

            ProductDTO product = new ProductDTO();
            product.setId(id);
            product.setName("Product not found on products microservice");
            product.setPrice(0.0);
            return Optional.of(new Item(product, 0));
        });
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Product not found on products microservice"));
    }
}
