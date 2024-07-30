package com.example.expensesharing.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.expensesharing.entity.Settlement;
import com.example.expensesharing.service.SettlementService;

@RestController
@RequestMapping("/api/settlements")
public class SettlementController {
    @Autowired
    private SettlementService settlementService;

    @GetMapping
    public ResponseEntity<List<Settlement>> getAllSettlements() {
        List<Settlement> settlements = settlementService.getAllSettlements();
        return ResponseEntity.ok(settlements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Settlement> getSettlementById(@PathVariable("id") Long settlementId) {
        Settlement settlement = settlementService.getSettlementById(settlementId);
        return ResponseEntity.ok(settlement);
    }

    @PostMapping
    public ResponseEntity<Settlement> createSettlement(@RequestBody Settlement settlement) {
        Settlement createdSettlement = settlementService.createSettlement(settlement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSettlement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Settlement> updateSettlement(@PathVariable("id") Long settlementId, @RequestBody Settlement settlementDetails) {
        Settlement updatedSettlement = settlementService.updateSettlement(settlementId, settlementDetails);
        return ResponseEntity.ok(updatedSettlement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSettlement(@PathVariable("id") Long settlementId) {
        settlementService.deleteSettlement(settlementId);
        return ResponseEntity.noContent().build();
    }
}