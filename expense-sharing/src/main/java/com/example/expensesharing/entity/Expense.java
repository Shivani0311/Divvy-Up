package com.example.expensesharing.entity;

import java.time.LocalDate;



import javax.persistence.*;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paid_by")
    private User paidBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paid_for")
    private User paidFor;

    private double amount;
    private LocalDate date;
    private String description;
    
    public Expense() {}

	public Expense(Long id, User paidBy, User paidFor, double amount, LocalDate date, String description) {
		super();
		this.id = id;
		this.paidBy = paidBy;
		this.paidFor = paidFor;
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

	public User getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}

	public User getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(User paidFor) {
		this.paidFor = paidFor;
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
    
    

    // Constructors, Getters, and Setters
}