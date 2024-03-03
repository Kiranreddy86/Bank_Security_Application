package com.security.bank.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	private String status;
	private Double balance;
	private float interestRate;
	@Enumerated(EnumType.STRING)
	private BranchType branch;
	private String proof;
	private Date openinigDate;
	private Long accountNumber;
	@OneToOne(mappedBy = "account")
	private Nominee nominee;
	@OneToOne(mappedBy = "account")
	private Card card;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
}
