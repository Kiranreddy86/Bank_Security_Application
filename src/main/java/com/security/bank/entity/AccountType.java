package com.security.bank.entity;

public enum AccountType {
    SAVINGS("Savings"),
    CURRENT("Current"),
    PPF("PPF"),
    SALARY("Salary");

    private final String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

