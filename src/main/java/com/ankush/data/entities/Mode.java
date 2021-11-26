package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "mode")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Mode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "modeno")
    private String modeno;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    @Column(name = "payby")
    private String payby;

    @OneToMany(mappedBy = "mode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModeTransaction> modTransactions;

}