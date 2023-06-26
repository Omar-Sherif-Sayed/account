package com.nagarro.account.service.account;

import com.nagarro.account.model.response.account.AccountResponse;

import java.time.LocalDate;
import java.util.List;

public interface AccountService {

    List<AccountResponse> search(Long accountId,
                                 LocalDate dateFrom,
                                 LocalDate dateTo,
                                 Double amountFrom,
                                 Double amountTo);

}
