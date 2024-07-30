package com.example.expensesharing.service;

import com.example.expensesharing.entity.Expense;


import com.example.expensesharing.repository.ExpenseRepository;

import com.example.expensesharing.repository.UserRepository;

import com.example.expensesharing.entity.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService; // Inject UserService to access non-static method


    
    @Transactional(readOnly = true)
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        expenses.forEach(expense -> {
            User paidBy = expense.getPaidBy();
            User paidFor = expense.getPaidFor();
            userService.calculateUserTotals(paidBy);
            userService.calculateUserTotals(paidFor);
        });
        return expenses;
    }

    @Transactional(readOnly = true)
    public Expense getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + expenseId));
        User paidBy = expense.getPaidBy();
        User paidFor = expense.getPaidFor();
        userService.calculateUserTotals(paidBy);
        userService.calculateUserTotals(paidFor);
        return expense;
    }

    @Transactional
    public Expense createExpense(Expense expense) {
        // Fetch the full User objects from the database
        User paidBy = userRepository.findById(expense.getPaidBy().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + expense.getPaidBy().getId()));
        User paidFor = userRepository.findById(expense.getPaidFor().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + expense.getPaidFor().getId()));

        expense.setPaidBy(paidBy);
        expense.setPaidFor(paidFor);

        Expense createdExpense = expenseRepository.save(expense);

        // Update user totals after saving expense
        userService.calculateUserTotals(paidBy);
        userService.calculateUserTotals(paidFor);

        return createdExpense;
    }

    @Transactional
    public Expense updateExpense(Long expenseId, Expense expenseDetails) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + expenseId));

        // Fetch the full User objects from the database
        User paidBy = userRepository.findById(expenseDetails.getPaidBy().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + expenseDetails.getPaidBy().getId()));
        User paidFor = userRepository.findById(expenseDetails.getPaidFor().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + expenseDetails.getPaidFor().getId()));

        expense.setPaidBy(paidBy);
        expense.setPaidFor(paidFor);
        expense.setAmount(expenseDetails.getAmount());
        expense.setDate(expenseDetails.getDate());
        expense.setDescription(expenseDetails.getDescription());

        Expense updatedExpense = expenseRepository.save(expense);

        // Update user totals after updating expense
        userService.calculateUserTotals(paidBy);
        userService.calculateUserTotals(paidFor);

        return updatedExpense;
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}