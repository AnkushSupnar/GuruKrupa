package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "modetransaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ModeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "itemname")
    private String itemname;

    @Column(name = "metal")
    private String metal;

    @Column(name = "purity")
    private String purity;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "ghat")
    private Float ghat;

    @Column(name = "finalweight")
    private Float finalweight;

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name = "modeid")
    private Mode mode;

}