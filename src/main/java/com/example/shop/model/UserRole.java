package com.example.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User_Roles")
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name="roleId",referencedColumnName = "id")
    private Role role;
}
