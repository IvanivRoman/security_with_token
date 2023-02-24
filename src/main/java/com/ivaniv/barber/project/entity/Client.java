package com.ivaniv.barber.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String email;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy="client")
    private List<Records> records;
}

