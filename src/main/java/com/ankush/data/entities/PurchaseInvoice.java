package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchaseinvoice")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class PurchaseInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "invoiceno")
    private String invoiceno;

    @ManyToOne
    @JoinColumn(name = "partyid")
    private PurchaseParty purchaseParty;

    @Column(name = "nettotal")
    private Float nettotal;

    @Column(name = "labourcharges")
    private Float labourcharges;

    @Column(name = "othercharges")
    private Float othercharges;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "grandtotal")
    private Float grandtotal;

    @OneToMany(mappedBy = "purchaseinvoice", orphanRemoval = true)
    private List<PurchaseTransaction> transactions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "bankid")
    private Bank bank;

}