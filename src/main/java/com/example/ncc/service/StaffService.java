package com.example.ncc.service;

import com.example.ncc.Exception.UserNotFoundException;
import com.example.ncc.bean.StaffFromHRMResponse;
import com.example.ncc.dto.Staff.StaffDto;
import com.example.ncc.entity.Staff;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final MapStructMapper mapper;
    private final PasswordEncoder passwordEncoder;
    RestTemplate restTemplate = new RestTemplate();

    public Staff addStaff(StaffDto staffDto){
        Staff staff = mapper.staffDtoToStaff(staffDto);
        staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
        return staffRepository.save(staff);
    }

    public Page<StaffDto> getStaffByParam(String firstName, String lastName, Pageable pageable) {
        return staffRepository.getStaffSortedByParam(firstName, lastName, pageable).map(mapper::staffDto);
    }
    public List<Staff> savePublicStaffs(){
        ResponseEntity<StaffFromHRMResponse> staffFromHRMResponse = restTemplate.exchange(
                "http://hrm-api.dev.nccsoft.vn/api/services/app/Public/GetUserForCheckin",
                HttpMethod.GET,
                null,
                StaffFromHRMResponse.class
        );
        List<Staff> staffFromHRMResponses = Objects.requireNonNull(staffFromHRMResponse.getBody()).getResult();
        return staffRepository.saveAll(staffFromHRMResponses);
    }

    public Staff updateStaff(StaffDto staffDto){
        Staff staff = staffRepository.findById(staffDto.getId())
                .orElseThrow(()->{
                    return new UserNotFoundException("This staff does not exist");
                });
        staff.setEmail(staffDto.getEmail());
        staff.setFirstName(staffDto.getFirstName());
        staff.setLastName(staffDto.getLastName());
        staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
        return staffRepository.save(staff);
    }

    public void removeStaff(int id){
        Staff staff = staffRepository.findById(id)
                .orElseThrow(()->{
                    return new UserNotFoundException("This staff does not exist");
                });
        staffRepository.delete(staff);
    }
}
