package com.example.expensesharing.repository;



import org.springframework.data.jpa.repository.JpaRepository;



import com.example.expensesharing.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {


	
}
