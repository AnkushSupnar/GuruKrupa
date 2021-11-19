package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "item")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "itemname")
    private String itemname;

    @Column(name = "metal")
    private String metal;

    @Column(name = "purity")
    private String purity;

}