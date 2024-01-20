package com.gallery.backend.controller;

import com.gallery.backend.entity.Cart;
import com.gallery.backend.entity.Item;
import com.gallery.backend.repository.CartRepository;
import com.gallery.backend.repository.ItemRepository;
import com.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    JwtService jwtService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    // 특정 사용자에게 담긴 아이템 목록 가져옴
    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(required = false) String token) {
        // 토큰 유효하지 않으면 401 return 해준다.
        if (!jwtService.isValid(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // 토큰 유효하면 memberId 가져온다.
        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        //--→ 근데 이렇게만 가져오면, 장바구니를 열었을 때 아이템이 보여지는 것이 아니라
        //    해당 아이템의 itemId, memberId 를 가져올 것이다.
        //    어떤 아이템을 담았는지는 알 수 없다.

        // 그래서 item id만 추출해서 → item name 가져올 것이다.
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(@PathVariable int itemId, @CookieValue(required = false) String token) {
        if (!jwtService.isValid(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        // 카트 없으면, 새로운 카트 생성한다. (쿠키에서 받은 memberId, itemId 사용해서)
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
