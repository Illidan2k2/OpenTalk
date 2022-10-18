package com.example.ncc.service;

import com.example.ncc.Exception.AuthenticateFailedException;
import com.example.ncc.Exception.OpenTalkNotFoundException;
import com.example.ncc.Exception.UserNotFoundException;
import com.example.ncc.dto.opentalk.OpentalkDto;
import com.example.ncc.dto.user.UserDto;
import com.example.ncc.dto.user.UserOpentalkDto;
import com.example.ncc.entity.Branch;
import com.example.ncc.entity.Opentalk;
import com.example.ncc.entity.Role;
import com.example.ncc.entity.User;
import com.example.ncc.enumeration.Status;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.BranchRepository;
import com.example.ncc.repository.OpentalkRepository;
import com.example.ncc.repository.RoleRepository;
import com.example.ncc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final OpentalkRepository opentalkRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final MapStructMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public Set<SimpleGrantedAuthority> getRole(Optional<User> user) {
        Role role = user.get().getRole();
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        System.out.println("User " + username + " found!");
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), getRole(user));
        System.out.println(userDetails);
        return userDetails;
    }

    public User addUser(UserDto userDto) {
        Role role = roleRepository.findById(userDto.getRoledto().getId())
                .orElseThrow();
        Branch branch = branchRepository.findById(userDto.getBranchDTO().getId())
                .orElseThrow();
        User user = mapper.userDtoToUser(userDto);
        user.setRole(role);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Page<UserDto> getAllUserDTO(String username, Status status, String branch, Pageable pageable) {
        //return userRepository.findAll(pageable).map(mapper::userDto);
        // status = status != null ? status : Status.UNKNOWN;
        branch = branch != null ? branch : "";
        username = username != null ? username : "";
        List<UserDto> userDtoList = userRepository.getUserByParam(username, status, branch)
                .stream()
                .map(mapper::userDto)
                .toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), userDtoList.size());
        final Page<UserDto> page = new PageImpl<>(userDtoList.subList(start, end), pageable, userDtoList.size());
        return page;
    }


    public Page<OpentalkDto> viewJoinedOpentalkByUser(Integer id, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("This user does not exist!");
        }
        //return mapper.userOpentalkDto(user.get());
        List<OpentalkDto> opentalkDtos = opentalkRepository.getOpentalkByUser(id, startDate, endDate)
                .stream()
                .map(mapper::opentalkDto)
                .sorted(Comparator.comparing(OpentalkDto::getDate))
                .toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), opentalkDtos.size());
        final Page<OpentalkDto> page = new PageImpl<>(opentalkDtos.subList(start, end), pageable, opentalkDtos.size());
        return page;
    }

    /*@Transactional(dontRollbackOn = ArithmeticException.class)*/
    public User updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> {
                    return new UserNotFoundException("This user does not exist");
                });
        Role role = roleRepository.findById(userDto.getRoledto().getId())
                .orElseThrow();
        Branch branch = branchRepository.findById(userDto.getBranchDTO().getId())
                .orElseThrow();
        user.setUsername(mapper.userDtoToUser(userDto).getUsername());
        user.setEmail(mapper.userDtoToUser(userDto).getEmail());
        user.setPhone_number(mapper.userDtoToUser(userDto).getPhone_number());
        user.setAddress(mapper.userDtoToUser(userDto).getAddress());
        user.setStatus(mapper.userDtoToUser(userDto).getStatus());
        user.setRole(role);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(mapper.userDtoToUser(userDto).getPassword()));
        ;
        return userRepository.save(user);
        /*throw new ArithmeticException();*/
    }

    public void deleteUser(Integer id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = userRepository.findById(id).get();
            userRepository.deleteById(id);
        }
    }

    public User findByIdAndName(int id, String username) {
        return userRepository.findByIdAndName(id, username);
    }

    public Opentalk joinOpentalk(int id) throws AuthenticateFailedException, OpenTalkNotFoundException {
        Optional<Opentalk> optional_opentalk = opentalkRepository.findById(id);
        if (optional_opentalk.isEmpty()) {
            throw new OpenTalkNotFoundException("This open talk does not exist yet!");
        }
        Opentalk existing_opentalk = optional_opentalk.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new AuthenticateFailedException("Authentication is not valid!");
        }
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userRepository.findUserByUsername(userDetails.getUsername()).get();
            existing_opentalk.getUsers().add(user);
        }
        return opentalkRepository.save(existing_opentalk);
    }

    public Page<UserOpentalkDto> getUnregisteredUser(String username, String branch,
                                                     LocalDate startDate, LocalDate endDate,
                                                     Pageable pageable) {
        List<UserOpentalkDto> registeredUsers = userRepository.getUnregisteredUser(username, branch, startDate, endDate)
                .stream()
                .map(mapper::userOpentalkDto)
                .filter(userOpentalkDto -> userOpentalkDto.getOpentalkDtos().size() >= 1)
                .toList();
        List<UserOpentalkDto> unregisteredUsers = new ArrayList<>(userRepository.findAll()
                .stream()
                .map(mapper::userOpentalkDto)
                .filter(userOpentalkDto -> userOpentalkDto.getStatus().equals(Status.ENABLED))
                .toList());
        unregisteredUsers.removeAll(registeredUsers);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), unregisteredUsers.size());
        final Page<UserOpentalkDto> page = new PageImpl<>(unregisteredUsers.subList(start, end), pageable, unregisteredUsers.size());
        return page;
    }

    public UserOpentalkDto opentalkRandomDial(LocalDate startDate, LocalDate endDate) {
        List<UserOpentalkDto> registeredUsers = userRepository.getUnregisteredUserByDate(startDate, endDate)
                .stream()
                .map(mapper::userOpentalkDto)
                .filter(userOpentalkDto -> userOpentalkDto.getOpentalkDtos().size() >= 1)
                .toList();
        List<UserOpentalkDto> unregisteredUsers = new ArrayList<>(userRepository.findAll()
                .stream()
                .map(mapper::userOpentalkDto)
                .filter(userOpentalkDto -> userOpentalkDto.getStatus().equals(Status.ENABLED))
                .toList());
        unregisteredUsers.removeAll(registeredUsers);
        if (unregisteredUsers.isEmpty()) {
            Integer leastOpentalkJoined = Collections
                    .min(registeredUsers.stream().map(userOpentalkDto -> userOpentalkDto.getOpentalkDtos().size()).toList());
            List<UserOpentalkDto> leastJoinedUsers = registeredUsers
                    .stream()
                    .filter(userOpentalkDto -> userOpentalkDto.getOpentalkDtos().size() == leastOpentalkJoined)
                    .toList();
            if(leastJoinedUsers.size()>1){
                return leastJoinedUsers.get(new Random().nextInt(leastJoinedUsers.size()));
            }
            else{
                return leastJoinedUsers.stream().findAny().get();
            }

        }
        return unregisteredUsers.get(new Random().nextInt(unregisteredUsers.size()));
    }


}