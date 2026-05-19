package com.example.gorido.Repository;

import com.example.gorido.Model.Admin;
import com.example.gorido.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByEmail(String email);

    @Query("SELECT a FROM Admin a WHERE a.email <> :email ORDER BY a.registeredDate DESC")
    List<Admin> findAllExceptEmail(@Param("email") String email);

    @Query("SELECT a FROM Admin a WHERE a.email = :email AND a.email <> :oldEmail")
    Optional<Admin> findByEmailAndNotOldEmail(@Param("email") String email,
                                              @Param("oldEmail") String oldEmail);
}
