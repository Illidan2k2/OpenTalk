package com.example.ncc.service;

import com.example.ncc.Exception.BranchNotFoundException;
import com.example.ncc.dto.branch.BranchDto;
import com.example.ncc.entity.Branch;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BranchService {
    private final BranchRepository branchRepository;
    private final MapStructMapper mapper;

    public Branch addBranch(BranchDto branchDto) {
        Branch branch = mapper.branchDtoToBranch(branchDto);
        return branchRepository.save(branch);
    }

    public Page<BranchDto> viewAllBranchDTO(Pageable pageable) {
        return branchRepository.findAll(pageable).map(mapper::branchDto);
    }

    public Page<BranchDto> viewBranchByIdAndName(Integer id, String name,Pageable pageable){
        return branchRepository.getBranchByIdOrName(id,name,pageable).map(mapper::branchDto);
    }

    public BranchDto updateBranch(BranchDto branchDto) {
        Branch branch = branchRepository.findById(branchDto.getId())
                .orElseThrow(() -> {
                    return new BranchNotFoundException("This branch does not exist yet!");
                });
        branch.setName(branchDto.getName());
        branchRepository.save(branch);
        return branchDto;
    }

    public void removeBranch(int id) {
        Optional<Branch> optional_branch = branchRepository.findById(id);
        if(optional_branch.isEmpty()){
            throw new BranchNotFoundException("This branch does not exist!");
        }
        branchRepository.delete(optional_branch.get());
    }
}
