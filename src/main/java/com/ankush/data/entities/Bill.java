package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "bill")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
@Builder
public class Bill {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "billno", unique = true)
    private String billno;

    @Column(name = "date")
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(name = "mod_id")
    private String modno;

    @Column(name = "modeamount")
    private Float modeamount;

    @Column(name = "billamount")
    private Float billamount;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "paid")
    private Float paid;

    @Column(name = "paymode")
    private String paymode;


    @OneToMany(mappedBy = "bill", orphanRemoval = true)
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "login_id")
    private Login login;


}