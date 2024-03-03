package com.security.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycDto {
	private String name;

	private String address;

	private Long number;

	private String identityProof;

}
