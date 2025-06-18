package com.guido.guzman.msv.items.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
//    @Bean
//    @LoadBalanced
//    WebClient.Builder webClient() {
//        return WebClient.builder().baseUrl(url);
//    }
    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder,
                        @Value("${config.baseurl.endpoint.products}") String url,
                        ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return webClientBuilder.baseUrl(url).filter(lbFunction).build();
    }
}
