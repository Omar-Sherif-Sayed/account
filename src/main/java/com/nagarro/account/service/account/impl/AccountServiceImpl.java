package com.nagarro.account.service.account.impl;

import com.nagarro.account.entity.account.Account;
import com.nagarro.account.entity.account.Statement;
import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;
import com.nagarro.account.mapper.account.AccountResponseMapper;
import com.nagarro.account.model.response.account.AccountResponse;
import com.nagarro.account.repository.account.AccountRepository;
import com.nagarro.account.repository.account.StatementRepository;
import com.nagarro.account.security.util.SecurityApplicationUtil;
import com.nagarro.account.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final StatementRepository statementRepository;

    @Override
    public List<AccountResponse> search(Long accountId, LocalDate dateFrom, LocalDate dateTo, Double amountFrom, Double amountTo) {

        checkUserAuthority(accountId, dateFrom, dateTo, amountFrom, amountTo);

        List<Account> accounts = accountRepository.findAll();

        List<Statement> statements = statementRepository.findAll();

        if (accountId == null && dateFrom == null && dateTo == null && amountFrom == null && amountTo == null)
            return lastThreeMonthsStatement(accounts, statements);

        if (accountId != null)
            statements = filterByAccountId(accountId, statements);

        if (dateFrom != null && dateTo != null)
            statements = filterByDate(dateFrom, dateTo, statements);

        if (amountFrom != null && amountTo != null)
            statements = filterByAmount(amountFrom, amountTo, statements);

        List<Statement> finalStatements = statements;
        List<Account> finalAccounts = accounts.stream()
                .filter(account -> finalStatements
                        .stream()
                        .anyMatch(statement -> statement.getAccountId().equals(account.getId())))
                .toList();

        return toAccountResponses(finalStatements, finalAccounts);
    }

    private static List<Statement> filterByAmount(Double amountFrom, Double amountTo, List<Statement> statements) {
        return statements
                .stream()
                .filter(statement -> statement.getAmount() > amountFrom && statement.getAmount() < amountTo)
                .toList();
    }

    private static List<Statement> filterByAccountId(Long accountId, List<Statement> statements) {
        return statements
                .stream()
                .filter(statement -> accountId.equals(statement.getAccountId()))
                .toList();
    }

    private static List<Statement> filterByDate(LocalDate dateFrom, LocalDate dateTo, List<Statement> statements) {
        return statements
                .stream()
                .filter(statement -> (statement.getDateField().isEqual(dateFrom) || statement.getDateField().isAfter(dateFrom))
                        && (statement.getDateField().isEqual(dateFrom) || statement.getDateField().isBefore(dateTo)))
                .toList();
    }

    private static void checkUserAuthority(Long accountId, LocalDate dateFrom, LocalDate dateTo, Double amountFrom, Double amountTo) {
        var role = SecurityApplicationUtil.getRole();

        var unauthorizedUser = (accountId != null || dateFrom != null || dateTo != null || amountFrom != null || amountTo != null)
                && (role == null || role.equals("ROLE_USER"));

        if (unauthorizedUser)
            throw BaseException.builder()
                    .errorCode(ErrorCode.AUTH_USER_UNAUTHORIZED)
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .build();
    }

    private List<AccountResponse> lastThreeMonthsStatement(List<Account> accounts, List<Statement> statements) {

        List<Statement> statementList = statements
                .stream()
                .filter(element -> element
                        .getDateField()
                        .isAfter(LocalDate.now().minusMonths(3)))
                .toList();

        List<Account> accountList = accounts.stream()
                .filter(account -> statementList
                        .stream()
                        .anyMatch(statement -> statement.getAccountId().equals(account.getId())))
                .toList();

        return toAccountResponses(statementList, accountList);
    }

    private List<AccountResponse> toAccountResponses(List<Statement> statements, List<Account> accounts) {
        return accounts.stream()
                .map(account -> AccountResponseMapper
                        .toResponse(account,
                                statements
                                        .stream()
                                        .filter(statement -> statement.getAccountId().equals(account.getId()))
                                        .toList()))
                .toList();
    }

}
