package com.example.medical_facilities.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleEntity extends BaseIdEntity {
    private String userId;
    private Long roleId;
}
