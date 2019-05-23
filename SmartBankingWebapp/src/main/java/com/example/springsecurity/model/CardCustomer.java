package com.example.springsecurity.model;

import javax.persistence.Entity;
import javax.persistence.Transient;


public class CardCustomer {
    @Transient
    private String id,cardid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }
}
