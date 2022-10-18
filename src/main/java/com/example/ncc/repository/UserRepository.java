package com.example.ncc.repository;

import com.example.ncc.entity.Role;
import com.example.ncc.entity.User;
import com.example.ncc.enumeration.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
    // Native query
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.username = :username")
    User findByIdAndName(@Param("id") int id, @Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.role =:role")
    Page<User> findByRole(@Param("role") Role role, Pageable pageable);

    @Query("SELECT DISTINCT u from User u WHERE (:username = '' or u.username = :username) " +
            "and (:status IS NULL or u.status = :status) " +
            "and (:branch = '' or u.branch.name = :branch)")
    List<User> getUserByParam(@Param("username") String username,
                              @Param("status") Status status,
                              @Param("branch") String branch);

    @Query("SELECT DISTINCT u from User u JOIN u.opentalks o WHERE (:username = '' or u.username = :username)" +
            "and (u.status = 'ENABLED') " +
            "and (:branch = '' or u.branch.name = :branch)" +
            "and (CAST(o.date AS LocalDate) >= :startDate AND CAST(o.date AS LocalDate) <= :endDate) ")
    List<User> getUnregisteredUser(@Param("username") String username,
                                   @Param("branch") String branch,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT u from User u JOIN u.opentalks o WHERE (u.status = 'ENABLED') " +
            "and (CAST(o.date AS LocalDate) >= :startDate AND CAST(o.date AS LocalDate) <= :endDate) ")
    List<User> getUnregisteredUserByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
