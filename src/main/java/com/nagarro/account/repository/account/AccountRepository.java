package com.nagarro.account.repository.account;

import com.nagarro.account.entity.account.Account;

import java.util.List;

public interface AccountRepository {


    List<Account> findAll();

}
