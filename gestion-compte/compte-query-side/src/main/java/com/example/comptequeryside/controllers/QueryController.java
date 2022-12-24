package com.example.comptequeryside.controllers;

import com.example.comptequeryside.entities.Account;
import lombok.AllArgsConstructor;
import ma.commonapi.queries.GetAccountByIdQuery;
import ma.commonapi.queries.GetAllAccountsQuery;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("query/accounts")
@AllArgsConstructor
public class QueryController {

    private QueryGateway queryGateway;

    @GetMapping("/all")
    public List<Account> getAccounts(){
        List<Account> accounts = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return accounts;
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable String id){
        return queryGateway.query( new GetAccountByIdQuery(id), ResponseTypes.instanceOf(Account.class)).join();
    }
}
