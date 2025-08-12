package com.servico.agenda.dto;

import com.servico.agenda.model.Address;

public class AddressDTO {
 
    private Long id;
    private String street;
    private String number;
    private String state;
    private String city;
    private Long userId;

    public AddressDTO() {}

    public AddressDTO(Address address) {
       this.id = address.getId();
       this.street = address.getStreet();
       this.number = address.getNumber();
       this.state = address.getState();
       this.city = address.getCity();
       this.userId = address.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
