package com.gallery.backend.controller;

import com.gallery.backend.dto.OrderDto;
import com.gallery.backend.entity.Order;
import com.gallery.backend.repository.OrderRepository;
import com.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(@CookieValue(required = false) String token) {
        if (!jwtService.isValid(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        List<Order> orders = orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(@RequestBody OrderDto orderDto, @CookieValue(required = false) String token) {
        if (!jwtService.isValid(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Order newOrder = new Order();
        newOrder.setMemberId(jwtService.getId(token));
        newOrder.setName(orderDto.getName());
        newOrder.setAddress(orderDto.getAddress());
        newOrder.setPayment(orderDto.getPayment());
        newOrder.setCardNumber(orderDto.getCardNumber());
        newOrder.setItems(orderDto.getItems());

        orderRepository.save(newOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
