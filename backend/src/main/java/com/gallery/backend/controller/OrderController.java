package com.gallery.backend.controller;

import com.gallery.backend.dto.OrderDto;
import com.gallery.backend.entity.Order;
import com.gallery.backend.repository.CartRepository;
import com.gallery.backend.repository.OrderRepository;
import com.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(@CookieValue(required = false) String token) {
        if (!jwtService.isValid(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        int memberId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(@RequestBody OrderDto orderDto, @CookieValue(required = false) String token) {
        if (!jwtService.isValid(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        int memberId = jwtService.getId(token);

        Order newOrder = new Order();
        newOrder.setMemberId(jwtService.getId(token));
        newOrder.setName(orderDto.getName());
        newOrder.setAddress(orderDto.getAddress());
        newOrder.setPayment(orderDto.getPayment());
        newOrder.setCardNumber(orderDto.getCardNumber());
        newOrder.setItems(orderDto.getItems());

        orderRepository.save(newOrder);
        cartRepository.deleteByMemberId(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
