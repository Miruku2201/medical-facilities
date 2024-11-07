package com.example.medical_facilities.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseIdEntity {

    private String name;
}
