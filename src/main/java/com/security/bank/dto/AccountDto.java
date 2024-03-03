package com.security.bank.dto;


import com.security.bank.entity.Nominee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	
	private String accountTtype;
	private double balance;
	private String proof;
	private Nominee nominee;

}
