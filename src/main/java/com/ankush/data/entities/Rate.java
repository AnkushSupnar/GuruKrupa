package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "rate")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter@Setter
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "purity")
    private String purity;

    @Column(name = "metal")
    private String metal;

}