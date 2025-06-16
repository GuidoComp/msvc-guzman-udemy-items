package com.guido.guzman.msv.items.services;

import com.guidio.guzman.libs.msv.commons.entities.Product;
import com.guido.guzman.msv.items.clients.IProductFeignClient;
import com.guido.guzman.msv.items.models.Item;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ItemServiceFeign implements IItemService {

    private final IProductFeignClient client;

    public ItemServiceFeign(IProductFeignClient client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        return client.findAll()
                .stream()
                .map(product -> new Item(product, new Random().nextInt(1, 10)))
                .toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = client.detail(id);
            return Optional.of(new Item(product, new Random().nextInt(1, 10)));
        } catch (FeignException e) {
            return Optional.empty();
        }
    }

    @Override
    public Product save(Product productDTO) {
        return client.save(productDTO);
    }

    @Override
    public Product update(Product productDTO, Long id) {
        return client.update(productDTO, id);
    }

    @Override
    public void deleteById(Long id) {
        client.delete(id);
    }
}
