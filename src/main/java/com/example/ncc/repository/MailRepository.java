package com.example.ncc.repository;

import com.example.ncc.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail,Integer> {
    @Query("SELECT m from Mail m WHERE (:title = '' or m.title like :title) and" +
            " (CAST(m.date AS LocalDateTime) >= :startDate AND CAST(m.date AS LocalDateTime) <= :endDate)")
    List<Mail> viewAllMails(@Param("title") String title,
                            @Param("startDate") LocalDateTime startDate,
                            @Param("endDate") LocalDateTime endDate);
}
