package com.example.springsecurity.service;

import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Login;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
//    public int accountAuthentication(String username, String password);
    public Customer requestNewAccount(Customer customer, MultipartFile file);
    public Account findAccount(String accountNumber);
    public Account updateBalance(Account account);
	public int register(Login login,Account account);
}
