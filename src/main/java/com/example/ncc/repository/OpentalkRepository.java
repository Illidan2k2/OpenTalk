package com.example.ncc.repository;

import com.example.ncc.entity.Opentalk;
import com.example.ncc.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OpentalkRepository extends JpaRepository<Opentalk, Integer> {
    @Query("SELECT DISTINCT o FROM Opentalk o JOIN o.users u WHERE (:status is null or u.status = :status) and " +
            "(:branch = '' or o.branch.name like :branch) and " +
            "(:username = '' or u.username like :username) and " +
            "(CAST(o.date AS LocalDate) >= :startDate AND CAST(o.date AS LocalDate) <= :endDate) ")
    List<Opentalk> getPreviousOpentalk(@Param("branch") String branch,
                                      @Param("username") String username,
                                      @Param("status") Status status,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
    @Query("SELECT DISTINCT o FROM Opentalk o JOIN o.users u WHERE (:status is null or u.status = :status) and " +
            "(:branch = '' or o.branch.name like :branch) and " +
            "(:username = '' or u.username like :username)")
    List<Opentalk> getUpcomingOpentalk(@Param("branch") String branch,
                                       @Param("username") String username,
                                       @Param("status") Status status);

    @Query("SELECT DISTINCT o from Opentalk o JOIN o.users u WHERE (u.id = :id) and" +
            "(CAST(o.date AS LocalDate) >= :startDate AND CAST(o.date AS LocalDate) <= :endDate)")
    List<Opentalk> getOpentalkByUser(@Param("id") Integer id,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT o FROM Opentalk o WHERE (:id is null or o.id = :id) and " +
            "(CAST(o.date AS LocalDate) >= :startDate AND CAST(o.date AS LocalDate) <= :endDate)")
    List<Opentalk> getOpentalkInSelectedDate(@Param("id") Integer id,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}
