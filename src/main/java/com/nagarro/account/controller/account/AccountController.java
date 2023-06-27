package com.nagarro.account.controller.account;

import com.nagarro.account.model.response.BaseResponse;
import com.nagarro.account.model.response.account.AccountResponse;
import com.nagarro.account.service.account.AccountService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/search")
    @ApiResponse(responseCode = "200")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse<List<AccountResponse>>> search(@RequestParam(required = false) Long accountId,
                                                                      @RequestParam(required = false) LocalDate dateFrom,
                                                                      @RequestParam(required = false) LocalDate dateTo,
                                                                      @RequestParam(required = false) Double amountFrom,
                                                                      @RequestParam(required = false) Double amountTo) {
        return ResponseEntity
                .ok(new BaseResponse<>(accountService.search(accountId, dateFrom, dateTo, amountFrom, amountTo)));
    }

}
