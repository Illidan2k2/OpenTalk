package com.example.ncc.service;

import com.example.ncc.Exception.AuthenticateFailedException;
import com.example.ncc.dto.Mail.ReceivedMailDto;
import com.example.ncc.dto.Mail.SentMailDto;
import com.example.ncc.dto.user.UserMailDto;
import com.example.ncc.entity.Mail;
import com.example.ncc.entity.User;
import com.example.ncc.mapper.MapStructMapper;
import com.example.ncc.repository.MailRepository;
import com.example.ncc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailRepository mailRepository;
    private final UserRepository userRepository;
    private final MapStructMapper mapper;
    private final LocalDateTime dateTime = LocalDateTime.now();

    public Mail sendMessage(SentMailDto sentMailDto) {
        List<String> receiver_emails = sentMailDto.getReceivers().stream().map(UserMailDto::getEmail).toList();
        Set<User> receivers = new HashSet<>();
        for (String email : receiver_emails) {
            Optional<User> user = userRepository.findByEmail(email);
            user.ifPresent(receivers::add);
        }
        Mail mail = mapper.SentMailDtoToMail(sentMailDto);
        mail.setTitle(sentMailDto.getTitle());
        mail.setContent(sentMailDto.getContent());
        mail.setDate(dateTime);
        mail.setReceivers(receivers);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new AuthenticateFailedException("Authentication is not valid!");
        }
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            User sender = userRepository.findUserByUsername(userDetails.getUsername()).get();
            mail.setSender(sender);
        }
        return mailRepository.save(mail);
    }

    public Page<SentMailDto> viewSentMail(String title, LocalDateTime startDate,
                                          LocalDateTime endDate, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (Objects.isNull(authentication)) {
            throw new AuthenticateFailedException("Authentication is not valid!");
        }
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            user = userRepository.findUserByUsername(userDetails.getUsername()).get();
        }
        User sender = user;
        List<SentMailDto> sentMails = mailRepository
                .viewAllMails(title, startDate, endDate)
                .stream()
                .filter(mail -> mail.getSender()== sender)
                .map(mapper::sentMailDto)
                .toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), sentMails.size());
        final Page<SentMailDto> page = new PageImpl<>(sentMails.subList(start, end), pageable, sentMails.size());
        return page;
    }

    public Page<ReceivedMailDto> viewReceivedMail(String title, LocalDateTime startDate,
                                                  LocalDateTime endDate, Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (Objects.isNull(authentication)) {
            throw new AuthenticateFailedException("Authentication is not valid!");
        }
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            user = userRepository.findUserByUsername(userDetails.getUsername()).get();
        }
        User receiver = user;
        List<ReceivedMailDto> receivedMails = mailRepository
                .viewAllMails(title,startDate,endDate)
                .stream()
                .filter(mail -> mail.getReceivers().contains(receiver))
                .map(mapper::receivedMailDto)
                .toList();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), receivedMails.size());
        final Page<ReceivedMailDto> page = new PageImpl<>(receivedMails.subList(start, end), pageable, receivedMails.size());
        return page;
    }
}
