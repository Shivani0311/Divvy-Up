package com.example.expensesharing.controller;

import com.example.expensesharing.entity.Expense;
import com.example.expensesharing.entity.Settlement;
import com.example.expensesharing.entity.User;



import com.example.expensesharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/expenses")
    public ResponseEntity<List<Expense>> getUserExpenses(@PathVariable("id") Long userId) {
        List<Expense> expenses = userService.getUserExpenses(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}/settlements")
    public ResponseEntity<List<Settlement>> getUserSettlements(@PathVariable("id") Long userId) {
        List<Settlement> settlements = userService.getUserSettlements(userId);
        return ResponseEntity.ok(settlements);
    }
    
    @GetMapping("/{id}/totalSpentByUser")
    public ResponseEntity<Double> getTotalSpentByUser(@PathVariable("id") Long userId){
    	double totalSpent = userService.getTotalSpentByUser(userId);
    	return ResponseEntity.ok(totalSpent);
    }

    @GetMapping("/{id}/totalToPay")
    public ResponseEntity<Double> getTotalToPayByUser(@PathVariable("id") Long userId) {
        double totalToPay = userService.getTotalToPayByUser(userId);
        return ResponseEntity.ok(totalToPay);
    }

    @GetMapping("/{id}/totalPaid")
    public ResponseEntity<Double> getTotalPaidByUser(@PathVariable("id") Long userId) {
        double totalPaid = userService.getUserById(userId).getTotalPaid();
        return ResponseEntity.ok(totalPaid);
    }

    @GetMapping("/{id}/totalToReceive")
    public ResponseEntity<Double> getTotalToReceiveByUser(@PathVariable("id") Long userId) {
        double totalToReceive = userService.getTotalToReceiveByUser(userId);
        return ResponseEntity.ok(totalToReceive);
    }

    @GetMapping("/{id}/totalReceived")
    public ResponseEntity<Double> getTotalReceivedByUser(@PathVariable("id") Long userId) {
        double totalReceived = userService.getUserById(userId).getTotalReceived();
        return ResponseEntity.ok(totalReceived);
    }
}