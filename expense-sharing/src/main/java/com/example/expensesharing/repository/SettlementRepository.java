package com.example.expensesharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expensesharing.entity.Settlement;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {


}
