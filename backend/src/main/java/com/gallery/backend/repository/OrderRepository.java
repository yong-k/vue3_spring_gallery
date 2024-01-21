package com.gallery.backend.repository;

import com.gallery.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByMemberIdOrderByIdDesc(int memberId);
}
