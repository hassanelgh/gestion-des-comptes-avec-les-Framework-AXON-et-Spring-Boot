package com.example.comptequeryside.services;

import com.example.comptequeryside.entities.Account;
import com.example.comptequeryside.entities.Operation;
import com.example.comptequeryside.repositories.AccountRepository;
import com.example.comptequeryside.repositories.OperationRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.commonapi.enums.OperationType;
import ma.commonapi.events.AccountActivatedEvent;
import ma.commonapi.events.AccountCreatedEvent;
import ma.commonapi.events.AccountCreditedEvent;
import ma.commonapi.events.AccountDebitedEvent;
import ma.commonapi.queries.GetAccountByIdQuery;
import ma.commonapi.queries.GetAllAccountsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public  void  on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent  received");
        Account account=new Account();
        account.setBalance(event.getBalance());
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }

    @EventHandler
    public  void  on(AccountActivatedEvent event){
        log.info("AccountActivatedEvent  received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }


    @EventHandler
    public  void  on(AccountDebitedEvent event){
        log.info("AccountActivatedEvent  received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operationDebit=new Operation();
        operationDebit.setAccount(account);
        operationDebit.setType(OperationType.DEBIT);
        operationDebit.setAmount(event.getAmount());
        operationDebit.setDataOperation(event.getDateCreation());

        operationRepository.save(operationDebit);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);

    }

    @EventHandler
    public  void  on(AccountCreditedEvent event){
        log.info("AccountCreditedEvent  received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operationCredited=new Operation();
        operationCredited.setAccount(account);
        operationCredited.setType(OperationType.CREDIT);
        operationCredited.setAmount(event.getAmount());
        operationCredited.setDataOperation(event.getDateCreation());

        operationRepository.save(operationCredited);

        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);

    }


    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        log.info("GetAllAccountsQuery  received");

        return accountRepository.findAll();

    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        log.info("GetAccountByIdQuery  received");
        return accountRepository.findById(query.getId()).orElseThrow(() -> new RuntimeException(""));

    }


}
