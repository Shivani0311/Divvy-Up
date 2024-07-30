
package com.example.expensesharing.entity;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;

    @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Expense> expensesPaid = new ArrayList<>();

    @OneToMany(mappedBy = "paidFor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Expense> expensesReceived = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Settlement> settlementsPaid = new ArrayList<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Settlement> settlementsReceived = new ArrayList<>();
    
    @Transient
    private double totalToPay;

    @Transient
    private double totalPaid;

    @Transient
    private double totalToReceive;

    @Transient
    private double totalReceived;
    
    // Constructors, Getters, and Setters
    
    public User() {}

	public User(Long id, String name, String email, List<Expense> expensesPaid, List<Expense> expensesReceived,
			List<Settlement> settlementsPaid, List<Settlement> settlementsReceived, double totalToPay, double totalPaid,
			double totalToReceive, double totalReceived) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.expensesPaid = expensesPaid;
		this.expensesReceived = expensesReceived;
		this.settlementsPaid = settlementsPaid;
		this.settlementsReceived = settlementsReceived;
		this.totalToPay = totalToPay;
		this.totalPaid = totalPaid;
		this.totalToReceive = totalToReceive;
		this.totalReceived = totalReceived;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Expense> getExpensesPaid() {
		return expensesPaid;
	}

	public void setExpensesPaid(List<Expense> expensesPaid) {
		this.expensesPaid = expensesPaid;
	}

	public List<Expense> getExpensesReceived() {
		return expensesReceived;
	}

	public void setExpensesReceived(List<Expense> expensesReceived) {
		this.expensesReceived = expensesReceived;
	}

	public List<Settlement> getSettlementsPaid() {
		return settlementsPaid;
	}

	public void setSettlementsPaid(List<Settlement> settlementsPaid) {
		this.settlementsPaid = settlementsPaid;
	}

	public List<Settlement> getSettlementsReceived() {
		return settlementsReceived;
	}

	public void setSettlementsReceived(List<Settlement> settlementsReceived) {
		this.settlementsReceived = settlementsReceived;
	}

	public double getTotalToPay() {
		return totalToPay;
	}

	public void setTotalToPay(double totalToPay) {
		this.totalToPay = totalToPay;
	}

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	public double getTotalToReceive() {
		return totalToReceive;
	}

	public void setTotalToReceive(double totalToReceive) {
		this.totalToReceive = totalToReceive;
	}

	public double getTotalReceived() {
		return totalReceived;
	}

	public void setTotalReceived(double totalReceived) {
		this.totalReceived = totalReceived;
	}
 
}