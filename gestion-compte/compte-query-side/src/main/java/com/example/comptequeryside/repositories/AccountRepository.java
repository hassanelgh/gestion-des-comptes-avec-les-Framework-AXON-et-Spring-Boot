package com.example.comptequeryside.repositories;

import com.example.comptequeryside.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
