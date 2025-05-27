package com.guido.guzman.msv.items.clients;

import com.guido.guzman.msv.items.models.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "products")
public interface IProductFeignClient {
    @GetMapping
    List<ProductDTO> findAll();
    @GetMapping("/{id}")
    ProductDTO detail(@PathVariable Long id);
}
