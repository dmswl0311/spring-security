package com.leavelive.demo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String img_url;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 30)
    private String nickname;

    private String provider;
    private String providerId;

}
