package com.guido.guzman.msv.items.services;

import com.guido.guzman.msv.items.models.Item;
import com.guido.guzman.msv.items.models.ProductDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.*;

//@Primary
@Service
public class ItemServiceWebClient implements IItemService {
    private final WebClient.Builder client;

    public ItemServiceWebClient(WebClient.Builder client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        return this.client.build()
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .map(product -> new Item(product, new Random().nextInt(1, 10)))
                .collectList()
                .block();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
//        try {
            return Optional.of(
                    client.build()
                            .get()
                            .uri("/{id}", params)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(ProductDTO.class)
                            .map(product -> new Item(product, new Random().nextInt(1, 10)))
                            .block()
            );
//        } catch(WebClientException e) {
//            return Optional.empty();
//        }
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        return client.build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productDTO)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();
    }

    @Override
    public ProductDTO update(ProductDTO productDTO, Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        return client.build()
                .put()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productDTO)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        client.build()
                .delete()
                .uri("/{id}", params)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}