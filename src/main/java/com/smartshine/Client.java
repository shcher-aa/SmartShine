package com.smartshine;

import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String accessCode;
    private int cleaningTimeMinutes;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAccessCode() { return accessCode; }
    public void setAccessCode(String accessCode) { this.accessCode = accessCode; }

    public int getCleaningTimeMinutes() { return cleaningTimeMinutes; }
    public void setCleaningTimeMinutes(int cleaningTimeMinutes) { this.cleaningTimeMinutes = cleaningTimeMinutes; }
}
