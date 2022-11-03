package com.example.ncc.repositoryImp;

import com.example.ncc.entity.Branch;
import com.example.ncc.meta_model.Branch_;
import com.example.ncc.repository.BranchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class BranchRepositoryImpl implements BranchRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public Page<Branch> getBranchByIdOrName(Integer id, String name, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Branch> query = builder.createQuery(Branch.class);
        Root<Branch> root = query.from(Branch.class);

        Predicate condtion1 = builder.equal(root.get(Branch_.ID),id);
        Predicate condtion2 = builder.equal(root.get(Branch_.NAME),name);

        query.select(root).where(condtion1,condtion2);
        List<Branch> branches = em.createQuery(query).getResultList();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), branches.size());
        final Page<Branch> page = new PageImpl<>(branches.subList(start, end), pageable, branches.size());
        return page;
    }
}
