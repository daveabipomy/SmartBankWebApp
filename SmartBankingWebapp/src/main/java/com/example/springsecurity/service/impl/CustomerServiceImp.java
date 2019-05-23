package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.CustomerRepository;
import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll(){
        return customerRepository.findAll();

    }
    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }
    public Customer findById(long id){
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAllNotApproved() {
        List<Customer> notApproved=new ArrayList<>();
        for(Customer c:customerRepository.findAll()){
      if(!c.isApproval()){
    notApproved.add(c);
}
        }
        return  notApproved;
    }

    @Override
    public List<Customer> findAllApproved() {
        List<Customer> approved=new ArrayList<>();
        for(Customer c:customerRepository.findAll()){
            if(c.isApproval()){
                approved.add(c);
            }
        }
        return  approved;
    }

    @Override
    public List<Customer> findAllActiveCustomer() {
        List<Customer> active=new ArrayList<>();

        for(Customer c:customerRepository.findAll()){
            List<Account> list=new ArrayList<>();
            if(c.isApproval() ){
                for(Account a:c.getAccounts()){
                    if(a.getStatus().equals("active")){
list.add(a);
                    }
                }
                if(list.size()!=0){

                    active.add(c);
                }

            }
        }
        return  active;
    }
}
