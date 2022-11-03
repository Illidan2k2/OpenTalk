package com.example.ncc.repository;

import com.example.ncc.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Integer> {
    @Query(value = "SELECT * FROM staffs s WHERE (:firstName='' or s.firstname = :firstName) and" +
            "(:lastName='' or s.lastname = :lastName) ORDER BY s.id",nativeQuery = true)
    Page<Staff> getStaffSortedByParam(@Param("firstName") String firstName,
                                      @Param("lastName") String lastName,
                                      Pageable pageable);
}
