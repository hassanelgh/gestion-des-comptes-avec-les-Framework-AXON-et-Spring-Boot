package com.example.comptecommandside.controller;

import ma.commonapi.commands.CreateAccountCommand;
import ma.commonapi.commands.CreditAccountCommand;
import ma.commonapi.commands.DebitAccountCommand;
import ma.commonapi.dtos.CreateAccountRequestDTO;
import ma.commonapi.dtos.OperationAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/account")
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequest){
        System.out.printf(createAccountRequest.toString());
        return commandGateway.send(
                new CreateAccountCommand(
                        UUID.randomUUID().toString(),
                        createAccountRequest.getCurrency(),
                        createAccountRequest.getInitBalance()
                )
        );
    }

    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody OperationAccountRequestDTO creditAccountRequest){
        return commandGateway.send(
                new CreditAccountCommand(
                        creditAccountRequest.getAccountId(),
                        creditAccountRequest.getCurrency(),
                        creditAccountRequest.getAmount()
                )
        );
    }

    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody OperationAccountRequestDTO debitAccountRequest){
        return commandGateway.send(
                new DebitAccountCommand(
                        debitAccountRequest.getAccountId(),
                        debitAccountRequest.getCurrency(),
                        debitAccountRequest.getAmount()
                )
        );
    }



    @GetMapping(path ="/eventstore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }


    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<String>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
