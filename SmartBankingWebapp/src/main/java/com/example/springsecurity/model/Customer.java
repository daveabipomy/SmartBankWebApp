package com.example.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;
    @Column(name = "FirstName",nullable = false)
    @NotNull(message = "User Name may not be null")
    private String firstName;

    @Column(name = "LastName",nullable = false)
    @NotNull(message = "User Name may not be null")
    private String lastName;

    //    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "User Name may not be null")
    private LocalDate dob;
    @NotNull(message = "User Name may not be null")
    private String ssn;
    @NotNull(message = "User Name may not be null")
    private String phone;
    @NotNull(message = "User Name may not be null")
//    @Email(message="{errors.invalid_email}")
    private String Email;
    @NotNull(message = "User Name may not be null")
    private String sex;

    @ColumnDefault("false")
    private boolean approval;
//    @NotNull(message = "User Name may not be null")
    private String photo;

    @OneToMany(mappedBy = "customer",orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonManagedReference(value="customer-account")
    private List<Account> accounts;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Login login;

    @OneToOne
    private Address address;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }




}
