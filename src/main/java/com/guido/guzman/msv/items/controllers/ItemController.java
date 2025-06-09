package com.guido.guzman.msv.items.controllers;

import com.guido.guzman.msv.items.models.Item;
import com.guido.guzman.msv.items.models.ProductDTO;
import com.guido.guzman.msv.items.services.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {
    private final IItemService itemService;
    private final CircuitBreakerFactory cBreakerFactory;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Value("${configuracion.texto}")
    private String text;
    @Autowired
    private Environment env;

    public ItemController(@Qualifier("itemServiceWebClient") IItemService itemService, CircuitBreakerFactory cBreakerFactory) {
        this.itemService = itemService;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping("/fetch-configs")
    public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("text", text);
        json.put("port", port);
        logger.info(port);
        logger.info(text);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", env.getProperty("configuracion.autor.email"));
        }
        return ResponseEntity.ok(json);
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

    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethod")
    @GetMapping("/details/{id}")
    public ResponseEntity<?> detail2(@PathVariable Long id) {
        Optional<Item> item =  itemService.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Product not found on products microservice"));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethod2")
    @TimeLimiter(name = "items")
    @GetMapping("/details2/{id}")
    public CompletableFuture<?> detail3(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<Item> item =  itemService.findById(id);
            if (item.isPresent()) {
                return ResponseEntity.ok(item.get());
            }
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Product not found on products microservice"));
        });
    }

    public ResponseEntity<?> getFallBackMethod(Throwable error) {
        logger.error(error.getMessage());

        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Product not found on products microservice");
        product.setPrice(0.0);
        return ResponseEntity.ok(new Item(product, 0));
    }

    public CompletableFuture<?> getFallBackMethod2(Throwable error) {
        return CompletableFuture.supplyAsync(() -> {
            logger.error(error.getMessage());

            ProductDTO product = new ProductDTO();
            product.setId(1L);
            product.setName("Product not found on products microservice");
            product.setPrice(0.0);
            return ResponseEntity.ok(new Item(product, 0));
        });
    }
}
