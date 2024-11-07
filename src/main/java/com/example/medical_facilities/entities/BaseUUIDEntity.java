package com.example.medical_facilities.entities;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
public class BaseUUIDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
