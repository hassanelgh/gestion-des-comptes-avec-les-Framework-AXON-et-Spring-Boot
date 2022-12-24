package com.example.comptecommandside.aggregates;


import ma.commonapi.commands.CreateAccountCommand;
import ma.commonapi.commands.CreditAccountCommand;
import ma.commonapi.commands.DebitAccountCommand;
import ma.commonapi.enums.AccountStatus;
import ma.commonapi.events.AccountActivatedEvent;
import ma.commonapi.events.AccountCreatedEvent;
import ma.commonapi.events.AccountCreditedEvent;
import ma.commonapi.events.AccountDebitedEvent;
import ma.commonapi.exceptions.BalanceExceptions;
import ma.commonapi.exceptions.BalanceNotSufficeForDebit;
import ma.commonapi.exceptions.CreditException;
import ma.commonapi.exceptions.DebitException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private String currency;
    private double balance;
    private AccountStatus accountStatus;

    public AccountAggregate() {
    }


    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        if(command.getInitBalance()<0) throw new BalanceExceptions("balance doit être supérieur à zéro");

        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getCurrency(),
                command.getInitBalance(),
                AccountStatus.CREATED)
        );

    }


    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId=event.getId();
        this.balance=event.getBalance();
        this.currency=event.getCurrency();
        this.accountStatus=AccountStatus.CREATED;

        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.accountStatus=event.getStatus();
    }




    @CommandHandler
    public void handle(CreditAccountCommand command){
        if(command.getAmount()<0) throw new CreditException("le montant doit être supérieur à zéro");

        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getCurrency(),
                command.getAmount(),
                new Date()));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance+=event.getAmount();
    }



    @CommandHandler
    public void handle(DebitAccountCommand command){
        if(command.getAmount()<0) throw new DebitException("le montant doit être supérieur à zéro");
        if(this.balance - command.getAmount()<0) throw new BalanceNotSufficeForDebit("balance n'est pas suffisant ");

        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getCurrency(),
                command.getAmount(),
                new Date()));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance-=event.getAmount();
    }








}
