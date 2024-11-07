package com.example.medical_facilities.repositories;

import com.example.medical_facilities.entities.UserCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentialEntity, Long> {
    @Query("SELECT uc FROM UserCredentialEntity uc WHERE uc.userId = :userId")
    Optional<UserCredentialEntity> findByUserId(@Param("userId")String userId);

    @Query("SELECT uc FROM UserCredentialEntity uc WHERE uc.username = :username")
    Optional<UserCredentialEntity> findByUsername(@Param("username")String username);

    @Query("""
            SELECT CASE WHEN COUNT(uc) > 0 THEN true ELSE false END
            FROM UserCredentialEntity uc
            WHERE uc.username = :username
            """)
    boolean existsByUsername(@Param("username")String username);

    @Modifying //Được sử dụng để chỉ định rằng đây là một truy vấn thay đổi dữ liệu (không chỉ là truy vấn đọc).
    @Transactional //Đảm bảo rằng quá trình xóa diễn ra trong một transaction để tránh lỗi nếu có vấn đề xảy ra trong quá trình xóa.
    @Query("DELETE FROM UserCredentialEntity uc WHERE uc.userId = :userId")
    void deleteByUserId(@Param("userId")String userId);
}
