package com.guido.guzman.msv.items.clients;

import com.guido.guzman.msv.items.models.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "products")
public interface IProductFeignClient {
    @GetMapping
    List<ProductDTO> findAll();
    @GetMapping("/{id}")
    ProductDTO detail(@PathVariable Long id);
    @PostMapping
    ProductDTO save(@RequestBody ProductDTO productDTO);
    @PutMapping("/{id}")
    ProductDTO update(@RequestBody ProductDTO productDTO, @PathVariable Long id);
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
