package com.example.springsecurity.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Manager extends  Employee{
    private  double salary;

    @ManyToOne(targetEntity = Branch.class)
    private Branch branchm;


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Branch getBranchm() {
        return branchm;
    }

    public void setBranchm(Branch branchm) {
        this.branchm = branchm;
    }
}
