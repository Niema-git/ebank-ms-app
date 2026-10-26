package org.sid.ebankservice.services;

import org.sid.ebankservice.entities.BankAccount;
import org.sid.ebankservice.feign.CustomerRestClient;
import org.sid.ebankservice.model.Customer;
import org.sid.ebankservice.repositories.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;
    private CustomerRestClient  customerRestClient;

    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    @McpTool(description = "Get all bank account")
    public List<BankAccount> getAllBankAccounts() {

        return accountRepository.findAll();
    }
    @McpTool(description = "Get a bank account by id ")
    public BankAccount findBankAccountById(@McpToolParam(description = "bnak account id ") String id) {
         BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account not found!"));
         bankAccount.setCustomer(
                 customerRestClient.getCustomerById(bankAccount.getCustomerId()));
         return bankAccount;
    }
    @McpTool(description = "save a new bank account ")
    public BankAccount save(@McpToolParam(description = "the bank account to save (balance, type, customerId)") BankAccount bankAccount) {
        try {
            Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
            return accountRepository.save(bankAccount);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }

    }
}
