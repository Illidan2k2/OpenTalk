package com.example.ncc.service;

import com.example.ncc.Exception.OpenTalkNotFoundException;
import com.example.ncc.dto.opentalk.OpentalkContainUserDto;
import com.example.ncc.dto.opentalk.OpentalkDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.entity.Branch;
import com.example.ncc.entity.Opentalk;
import com.example.ncc.entity.User;
import com.example.ncc.enumeration.Status;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.BranchRepository;
import com.example.ncc.repository.OpentalkRepository;
import com.example.ncc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OpentalkService {
    private final OpentalkRepository opentalkRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final MapStructMapper mapper;

    public Opentalk addOpentalk(OpentalkDto opentalkDto) {
        Branch branch = branchRepository.findById(opentalkDto.getBranchDTO().getId())
                .orElseThrow();
        List<Integer> list_userId = opentalkDto.getUserDtos().stream().map(UserDto::getId).toList();
        Set<User> users = new HashSet<>();
        for (int id : list_userId) {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(users::add);
        }
        Opentalk opentalk = mapper.opentalkDtoToOpenTalk(opentalkDto);
        opentalk.setUsers(users);
        opentalk.setBranch(branch);
        return opentalkRepository.save(opentalk);
    }

    public Page<OpentalkDto> getAllOpentalkInSelectedDate(Integer id, LocalDate startDate,
                                                          LocalDate endDate, Pageable pageable) {
        List<OpentalkDto> opentalkDtos = opentalkRepository.getOpentalkInSelectedDate(id, startDate, endDate)
                .stream()
                .map(mapper::opentalkDto)
                .sorted(Comparator.comparing(OpentalkDto::getDate))
                .toList();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), opentalkDtos.size());
        return new PageImpl<>(opentalkDtos.subList(start, end), pageable, opentalkDtos.size());
    }

    public Page<OpentalkDto> getUpcomingOpentalk(String branch, String username, Status status, Pageable pageable){
        LocalDateTime dateTime = LocalDateTime.now();
        branch = branch !=null? branch : "";
        username = username != null? username : "";
        List<OpentalkDto> nextOpentalks = opentalkRepository.getUpcomingOpentalk(branch,username,status)
                .stream()
                .filter(opentalk -> opentalk.getDate().isAfter(dateTime))
                .map(mapper::opentalkDto)
                .toList();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), nextOpentalks.size());
        final Page<OpentalkDto> page = new PageImpl<>(nextOpentalks.subList(start, end), pageable, nextOpentalks.size());
        return page;
    }

    public Page<OpentalkDto> getPreviousOpentalk(LocalDate startDate,
                                                 String branch, String username,
                                                 Status status, Pageable pageable){
        LocalDateTime dateTime = LocalDateTime.now();
        branch = branch !=null? branch : "";
        username = username != null? username : "";
        List<OpentalkDto> nextOpentalks = opentalkRepository.getPreviousOpentalk(branch,username,status,startDate)
                .stream()
                .filter(opentalk -> opentalk.getDate().isBefore(dateTime))
                .map(mapper::opentalkDto)
                .toList();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), nextOpentalks.size());
        final Page<OpentalkDto> page = new PageImpl<>(nextOpentalks.subList(start, end), pageable, nextOpentalks.size());
        return page;
    }

    public OpentalkContainUserDto ViewUserInSelectedOpentalk(Integer id) {
        Optional<Opentalk> opentalk = opentalkRepository.findById(id);
        if (opentalk.isEmpty()) {
            throw new OpenTalkNotFoundException("This opentalk does not exist!");
        }
        return mapper.opentalkContainUserDto(opentalk.get());
    }

    public Opentalk updateOpentalkIndex(OpentalkDto opentalkDto) {
        Opentalk opentalk = opentalkRepository.findById(opentalkDto.getId())
                .orElseThrow(() -> {
                    return new OpenTalkNotFoundException("This opentalk does not exist!");
                });
        Branch branch = branchRepository.findById(opentalkDto.getBranchDTO().getId())
                .orElseThrow();
        List<Integer> list_userId = opentalkDto.getUserDtos().stream().map(UserDto::getId).toList();
        Set<User> users = new HashSet<>();
        for (int id : list_userId) {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(users::add);
        }
        opentalk.setTopic(mapper.opentalkDtoToOpenTalk(opentalkDto).getTopic());
        opentalk.setLink(mapper.opentalkDtoToOpenTalk(opentalkDto).getLink());
        opentalk.setBranch(branch);
        opentalk.setDate(mapper.opentalkDtoToOpenTalk(opentalkDto).getDate());
        opentalk.setUsers(users);
        return opentalkRepository.save(opentalk);
    }

    public void deleteOpentalk(Integer id) {
        Optional<Opentalk> optional_opentalk = opentalkRepository.findById(id);
        if (optional_opentalk.isEmpty()) {
            throw new OpenTalkNotFoundException("This opentalk does not exist yet!");
        }
        opentalkRepository.delete(optional_opentalk.get());
    }

    public Opentalk getNearestOpentalk(LocalDate date) {
        LocalDate customDate = date == null ? LocalDate.now() : date;
        return opentalkRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Opentalk::getDate))
                .filter(opentalk -> opentalk.getDate().isAfter(customDate.atStartOfDay()))
                .findFirst()
                .orElseThrow(() -> {
                    return new OpenTalkNotFoundException("There isn't any opentalk match the search.");
                });
    }
}

