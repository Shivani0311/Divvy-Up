package com.example.expensesharing.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.expensesharing.entity.Settlement;
import com.example.expensesharing.entity.User;
import com.example.expensesharing.repository.SettlementRepository;
import com.example.expensesharing.repository.UserRepository;

@Service
public class SettlementService {
    @Autowired
    private SettlementRepository settlementRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService; // Inject UserService to access non-static method

    @Transactional(readOnly = true)
    public List<Settlement> getAllSettlements() {
        List<Settlement> settlements = settlementRepository.findAll();
        settlements.forEach(settlement -> {
            User fromUser = settlement.getFromUser();
            User toUser = settlement.getToUser();
            userService.calculateUserTotals(fromUser);
            userService.calculateUserTotals(toUser);
        });
        return settlements;
    }

    @Transactional(readOnly = true)
    public Settlement getSettlementById(Long settlementId) {
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new RuntimeException("Settlement not found with id " + settlementId));
        User fromUser = settlement.getFromUser();
        User toUser = settlement.getToUser();
        userService.calculateUserTotals(fromUser);
        userService.calculateUserTotals(toUser);
        return settlement;
    }

    @Transactional
    public Settlement createSettlement(Settlement settlement) {
        // Fetch the full User objects from the database
        User fromUser = userRepository.findById(settlement.getFromUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + settlement.getFromUser().getId()));
        User toUser = userRepository.findById(settlement.getToUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + settlement.getToUser().getId()));

        settlement.setFromUser(fromUser);
        settlement.setToUser(toUser);

        Settlement createdSettlement = settlementRepository.save(settlement);

        // Update user totals after saving settlement
        userService.calculateUserTotals(fromUser);
        userService.calculateUserTotals(toUser);

        return createdSettlement;
    }

    @Transactional
    public Settlement updateSettlement(Long settlementId, Settlement settlementDetails) {
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new RuntimeException("Settlement not found with id " + settlementId));

        // Fetch the full User objects from the database
        User fromUser = userRepository.findById(settlementDetails.getFromUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + settlementDetails.getFromUser().getId()));
        User toUser = userRepository.findById(settlementDetails.getToUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + settlementDetails.getToUser().getId()));

        settlement.setFromUser(fromUser);
        settlement.setToUser(toUser);
        settlement.setAmount(settlementDetails.getAmount());
        settlement.setDate(settlementDetails.getDate());
        settlement.setDescription(settlementDetails.getDescription());

        Settlement updatedSettlement = settlementRepository.save(settlement);

        // Update user totals after updating settlement
        userService.calculateUserTotals(fromUser);
        userService.calculateUserTotals(toUser);

        return updatedSettlement;
    }
    
    @Transactional
    public void deleteSettlement(Long settlementId) {
        settlementRepository.deleteById(settlementId);
    }
}