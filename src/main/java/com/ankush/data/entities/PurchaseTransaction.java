package com.ankush.data.entities;

import lombok.*;
import lombok.ToString.Exclude;

import javax.persistence.*;

@Entity
@Table(name = "purchasetransaction")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class PurchaseTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchaseinvoiceid")
   // @Exclude
    private PurchaseInvoice purchaseinvoice;

    @Column(name = "hsn")
    private Long hsn;

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

    @Column(name = "majurirate")
    private Float majurirate;

    @Column(name = "majuri")
    private Float majuri;

    @Column(name = "amount")
    private Float amount;

}