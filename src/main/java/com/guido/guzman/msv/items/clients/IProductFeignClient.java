package com.guido.guzman.msv.items.clients;

import com.guidio.guzman.libs.msv.commons.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "products")
public interface IProductFeignClient {
    @GetMapping
    List<Product> findAll();
    @GetMapping("/{id}")
    Product detail(@PathVariable Long id);
    @PostMapping
    Product save(@RequestBody Product productDTO);
    @PutMapping("/{id}")
    Product update(@RequestBody Product productDTO, @PathVariable Long id);
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
