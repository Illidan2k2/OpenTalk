package com.example.ncc.schedule;

import com.example.ncc.entity.Opentalk;
import com.example.ncc.entity.User;
import com.example.ncc.repository.UserRepository;
import com.example.ncc.service.OpentalkService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InviteOpentalkLetter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSender javaMailSender;
    private final OpentalkService opentalkService;
    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;

    // second - minute - hour - day of month - month - days(s) of week
    @Scheduled(cron = "0 22 16 * * THU")
    @SchedulerLock(name = "TaskScheduler_scheduledTask",
            lockAtLeastFor = "PT1M", lockAtMostFor = "PT14M")
    public void sendEmailInvitationOpentalk() throws MessagingException {
        sendEmail();
    }
    @Async
    public String sendEmail() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        Opentalk opentalk = opentalkService.getNearestOpentalk(LocalDate.now());
        List<String> emailReceiverList = userRepository.findAll().stream().map(User::getEmail).toList();
        String[] emailReceivers = emailReceiverList.toArray(new String[emailReceiverList.size()]);
        Context context = new Context();
        context.setVariable("opentalk",opentalk);
        String process = templateEngine.process("opentalk",context);
        helper.setTo(emailReceivers);
        helper.setText(process,true);
        helper.setSubject("Opentalk Invitation");
        javaMailSender.send(message);
        return "Email Sent Sucessfully!";
    }
}
