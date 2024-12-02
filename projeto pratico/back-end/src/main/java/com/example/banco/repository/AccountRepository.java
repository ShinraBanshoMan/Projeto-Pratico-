package com.example.banco.repository;

import com.example.banco.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM account WHERE name = :name AND password = :password", nativeQuery = true)
    public Account findByUser(@Param("name") String name, @Param("password") String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET balance = :balance WHERE id = :accountId", nativeQuery = true)
    public void updateBalance(@Param("accountId") Long accountId, @Param("balance") Double balance);
}
