package com.ankush.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="login")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String username;
    private String password;


}
