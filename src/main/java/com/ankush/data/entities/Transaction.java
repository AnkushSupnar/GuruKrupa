package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "transaction")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Column(name = "itemname")
    private String itemname;

    @Column(name = "metal")
    private String metal;

    @Column(name = "purity")
    private String purity;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "majuri")
    private Float majuri;

    @Column(name = "amount")
    private Float amount;

}