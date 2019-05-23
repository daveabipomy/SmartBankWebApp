package com.example.springsecurity.service;

import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Login;
import com.example.springsecurity.model.Teller;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
//    public int accountAuthentication(String username, String password);
    public Customer requestNewAccount(Customer customer, MultipartFile file);
    public Account findAccount(String accountNumber);
    public Account findAccountByUserName(String userName);
    public Account updateBalance(Account account);
	public int registerCustomer(Login login,Account account);
	public int registerTeller(Login login, Teller teller);
}
