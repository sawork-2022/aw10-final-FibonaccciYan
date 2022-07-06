package com.micropos.carts.rest;

// import com.micropos.api.*;
// import com.micropos.dto.*;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;

import reactor.core.publisher.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@EnableDiscoveryClient
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public Flux<Item> listItems() {
        return this.cartService.getItems();
    }

    @PostMapping(value = "/{productId}")
    public Mono<ResponseEntity<Boolean>> putProductById(@PathVariable String productId) {
        boolean flag = cartService.putItem(productId);
        if (flag)
            return Mono.just(new ResponseEntity<>(flag, HttpStatus.CREATED));
        else
            return Mono.just(new ResponseEntity<>(flag, HttpStatus.NOT_FOUND));
    }
}
