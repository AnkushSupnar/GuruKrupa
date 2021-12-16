package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="itemstock")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ItemStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemid")
    private Item item;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "purchaserate")
    private Float purchaserate;

}
