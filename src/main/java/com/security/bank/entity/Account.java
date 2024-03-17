package com.security.bank.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	@JsonIgnore
	@OneToMany(mappedBy = "account", targetEntity = Investment.class)
	private List investmentList = new ArrayList<>();
}
