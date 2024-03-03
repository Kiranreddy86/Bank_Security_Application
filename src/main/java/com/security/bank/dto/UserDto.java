package com.security.bank.dto;

import java.util.ArrayList;
import java.util.List;

import com.security.bank.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private String name;

	private String username;

	private String password;

	private String address;

	private Long number;

	private String identityProof;
	private List<Account> accountList = new ArrayList<>();

	private List<Account> investmentList = new ArrayList<>();

}
