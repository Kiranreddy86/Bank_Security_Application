package com.security.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NomineeDto {
	
	private String relation;

	private String name;

	private Long accountNumber;

	private String gender;

	private int age;

}
