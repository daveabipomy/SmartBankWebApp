package com.example.springsecurity.service.impl;
import com.example.springsecurity.dao.*;
import com.example.springsecurity.model.*;
import com.example.springsecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService {


    @Autowired
    private RequestNewAccountRepository requestNewAccountRepository;

    @Autowired
    private AccountServiceImp accountServiceImp;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountRepostitory accountRepostitory;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UploadServiceImpl uploadService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private TellerRepository tellerRepository;



    private Session getSession(){
        Session session = null;

        try {
            session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        }catch (Exception e){
            e.printStackTrace();
        }

        return session;
    }

    @Override
    public Account findAccountByUserName(String userName) {
        Login login=loginRepository.findByUserName(userName);
        Customer customer=customerRepository.findCustomerByLogin(login);
        Account account=accountRepostitory.findAccountByCustomer(customer).get(0);
        return account;
    }

    public int registerTeller(Login login, Teller teller)
    {
        Login userName=checkEmail(login.getUserName());

        if(userName==null)
        {
            if(teller.getLogin()==null) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                login.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
                Login log = loginRepository.save(login);
                teller.setLogin(log);
                Session session = null;
                session = getSession();
                session.beginTransaction();
                session.merge(teller);
                session.getTransaction().commit();
                getSession().close();
                return 1;
            }
            return 2;
        }
    return  0;
    }

    public Teller checkTeller(String empid)
    {
        return tellerRepository.findByEmployeeId(empid);
    }


    public int registerCustomer(Login login,Account checkAccount) {

        Customer customer=customerRepository.findById(checkAccount.getCustomer().getId()).orElse(null);

        Login userName=checkEmail(login.getUserName());
        if(customer.isApproval()==true) {
            if(customer.getLogin()==null) {
                if(userName==null) {
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    login.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
                    Login log = loginRepository.save(login);
                    customer.setLogin(log);
                    Session session = null;
                    session = getSession();
                    session.beginTransaction();
                    session.merge(customer);
                    session.getTransaction().commit();
                    getSession().close();
                    return 1;
                }
                else
                {
                    return 4;
                }
            }

            else
            {
                return  2;
            }
        }
        return 3;
    }

    public Account checkAccount(String accountNumber)
    {
        Account getAccount=accountRepostitory.findByAccountNumber(accountNumber);
       return  getAccount;
    }



    public Login checkEmail(String userName)
    {
        return loginRepository.findByUserName(userName);
    }

    public Customer requestNewAccount(Customer customer, MultipartFile file)
    {
        String thumbnailName=uploadService.uploadImage(file);
        System.out.println("profile pic  "+thumbnailName);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/smart/downloadFile/")
                .path(thumbnailName)
                .toUriString();
        Address address=new Address();
        address.setStreet(customer.getAddress().getStreet());
        address.setCity(customer.getAddress().getCity());
        address.setState(customer.getAddress().getState());
        address.setZip(customer.getAddress().getZip());
        addressRepository.save(address);
        Customer cust=new Customer();
        cust.setFirstName(customer.getFirstName());
        cust.setLastName(customer.getLastName());
        cust.setSex(customer.getSex());
        cust.setSsn(customer.getSsn());
        cust.setPhoto(fileDownloadUri);
        cust.setDob(customer.getDob());
        cust.setEmail(customer.getEmail());
        cust.setPhone(customer.getPhone());
        cust.setAddress(address);
        System.out.println(customer.getDob());
        return  requestNewAccountRepository.save(cust);
    }

    @Override
    public Account findAccount(String accountNumber) {
        return accountRepostitory.findByAccountNumber(accountNumber);
    }




    public Account updateBalance(Account account)
    {
        Session session = null;

        try {
            session = getSession();
            session.beginTransaction();
            session.merge(account);
            session.getTransaction().commit();
            getSession().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;


    }

}
