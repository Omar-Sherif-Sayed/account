package com.nagarro.account.repository.account;

import com.nagarro.account.entity.account.Statement;

import java.util.List;

public interface StatementRepository {

    List<Statement> findAll();

}
