package com.example.medical_facilities.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserCredentialEntity extends BaseIdEntity {
    private String userId;
    private String username;
    private String password;
}
