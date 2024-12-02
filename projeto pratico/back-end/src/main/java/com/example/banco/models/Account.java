package com.example.banco.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = true)
    private float excessLimit;
    @Column(nullable = true)
    private float income;



    public Account() {
    }
}
