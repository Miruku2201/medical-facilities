package com.example.medical_facilities.repositories;

import com.example.medical_facilities.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM RoleEntity r
            WHERE r.name = :name
            """)
    //* Explain: Choosing all the role with "role_name". In case that the number of returned value is bigger than 0, function return true or vice versa
    boolean existsByName(@Param("name") String name);

    @Query("SELECT r FROM RoleEntity r WHERE r.name = :name")
    Optional<RoleEntity> findByName(@Param("name") String name);
}
