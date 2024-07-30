package com.example.expensesharing.entity;


import java.time.LocalDate;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "from_user")
    @JsonIgnoreProperties({"settlementsPaid", "settlementsReceived"})
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user")
    @JsonIgnoreProperties({"settlementsPaid", "settlementsReceived"})
    private User toUser;
    
    private double amount;
    private LocalDate date;
    private String description;

    // Constructors, Getters, and Setters
    
    public Settlement() {}

	public Settlement(Long id, User fromUser, User toUser, double amount, LocalDate date, String description) {
		super();
		this.id = id;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.amount = amount;
		this.date = date;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}