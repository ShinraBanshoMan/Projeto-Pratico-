package com.example.banco.controllers;

import com.example.banco.dto.BalanceMovementDTO;
import com.example.banco.dto.RequestErrorDTO;
import com.example.banco.dto.UserDTO;
import com.example.banco.models.Account;
import com.example.banco.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController
{
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account){
        Account accountExisted = accountRepository.findByUser(account.getName(), account.getPassword());
        if (accountExisted == null) {
            Account newAccount = accountRepository.save(account);
            return ResponseEntity.ok(newAccount);
        } else {
            RequestErrorDTO error = new RequestErrorDTO("Usuário já possui conta!", "BAD_REQUEST");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PatchMapping("/balance-movement")
    public ResponseEntity<?> balanceMovement(@RequestBody BalanceMovementDTO balanceMovement){
        boolean accountExisted = accountRepository.existsById(balanceMovement.accountId);
        if (accountExisted) {
            accountRepository.updateBalance(balanceMovement.accountId, balanceMovement.balance);
            Account account = accountRepository.findById(balanceMovement.accountId).get();
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/auth")
    public ResponseEntity<Account> getByUser(@RequestBody UserDTO user){
        Account account = accountRepository.findByUser(user.name, user.password);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
