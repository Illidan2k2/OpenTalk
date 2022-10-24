package com.example.ncc.repository;

import com.example.ncc.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Page<Branch> getBranchByIdOrName(Integer id, String name, Pageable pageable);
}
