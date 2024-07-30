package com.example.expensesharing.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.expensesharing.entity.User;
import com.example.expensesharing.entity.Expense;
import com.example.expensesharing.entity.Settlement;

import com.example.expensesharing.repository.UserRepository;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    
    
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(this::calculateUserTotals); // Calculate totals for each user
        return users;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        calculateUserTotals(user); // Calculate totals for the specific user
        return user;
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, User userDetails) {
        User user = getUserById(userId);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public List<Expense> getUserExpenses(Long userId) {
        User user = getUserById(userId);
        return user.getExpensesPaid();
    }

    @Transactional(readOnly = true)
    public List<Settlement> getUserSettlements(Long userId) {
        User user = getUserById(userId);
        return user.getSettlementsReceived();
    }

    @Transactional(readOnly = true)
    public double getTotalSpentByUser(Long userId) {
        User user = getUserById(userId);
        return user.getExpensesPaid().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    @Transactional(readOnly = true)
    public double getTotalToReceiveByUser(Long userId) {
        User user = getUserById(userId);
        calculateUserTotals(user); // Ensure totals are calculated before retrieving
        return user.getTotalToReceive();
    }

    @Transactional(readOnly = true)
    public double getTotalToPayByUser(Long userId) {
        User user = getUserById(userId);
        calculateUserTotals(user); // Ensure totals are calculated before retrieving
        return user.getTotalToPay();
    }

    // Helper method to calculate totalToPay, totalPaid, totalToReceive, and totalReceived for a user
    public void calculateUserTotals(User user) {
        // Calculate total paid from expenses
        double totalPaid = user.getExpensesPaid().stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        // Calculate total received from expenses
        double totalReceived = user.getExpensesReceived().stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        // Calculate total settlements paid by the user
        double settlementsPaid = user.getSettlementsPaid().stream()
                .mapToDouble(Settlement::getAmount)
                .sum();

        // Calculate total settlements received by the user
        double settlementsReceived = user.getSettlementsReceived().stream()
                .mapToDouble(Settlement::getAmount)
                .sum();

        // Update user's total paid and received including settlements
        double totalPaidIncludingSettlements = totalPaid + settlementsPaid;
        double totalReceivedIncludingSettlements = totalReceived + settlementsReceived;

        // Calculate total to pay and receive
        double totalToPay = totalReceivedIncludingSettlements - totalPaidIncludingSettlements;
        double totalToReceive = totalPaidIncludingSettlements - totalReceivedIncludingSettlements;

        // Update user's totals
        user.setTotalPaid(totalPaidIncludingSettlements);
        user.setTotalReceived(totalReceivedIncludingSettlements);
        user.setTotalToPay(totalToPay);
        user.setTotalToReceive(totalToReceive);
    }

}