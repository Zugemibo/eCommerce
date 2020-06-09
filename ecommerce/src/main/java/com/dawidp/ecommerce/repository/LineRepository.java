package com.dawidp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawidp.ecommerce.entity.OrderLine;

public interface LineRepository extends JpaRepository<OrderLine, Long> {

}
