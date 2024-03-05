package com.security.bank.repository;

import java.util.List;
import java.util.Optional;

import com.security.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.BranchType;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long>{
	Optional<Account> findByAccountNumber(Long accountNumber);
	@Query(value = "SELECT * FROM account where status=Active",nativeQuery = true)
	List<Account>findAllActiveAccounts();
	@Query(value = "SELECT * FROM account where status!=Active",nativeQuery = true)
	List<Account>findAllInActiveAccounts();
	@Query(value = "SELECT * FROM account where account_type=: type",nativeQuery = true)
	List<Account>findAllByAccountType(@Param("type") AccountType accountType);
	@Query(value = "SELECT * FROM account where branch=: type",nativeQuery = true)
	List<Account>findAllByBranch(@Param("type") BranchType branchType);
	@Query(value = "SELECT * FROM account where user_id=: userId",nativeQuery = true)
    List<Account> getAllById(@Param("userId") Long userId);
	Optional<Account> findByAccountnumber(Long accountNumber);
}
