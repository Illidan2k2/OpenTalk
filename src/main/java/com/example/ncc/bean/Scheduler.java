package com.example.ncc.bean;

import com.example.ncc.entity.Chat;
import com.example.ncc.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {
    Logger logger = Logger.getLogger(Scheduler.class.getName());
    private final ChatRepository chatRepository;
    // second - minute - hour - day of month - month - days(s) of week
    @Scheduled(cron = "0 0 9 * *     1-5")
    public void goodMorning(){
        System.out.println("Schedule is running!");
        chatRepository.save(new Chat("Bot","Good morning!"));
    }

    /*private static int i = 0;

    @Scheduled(initialDelay=1000, fixedDelay=5000)
    public void testScheduling() throws InterruptedException {
        System.out.println("Started : "+ ++i);
        Thread.sleep(4000);
        System.out.println("Finished : "+ i);
    }*/

    /*private static int i = 0;

    @Scheduled(initialDelay=1000, fixedDelay=1000)
    public void testScheduling() throws InterruptedException {
        System.out.println("Started : "+ ++i);
        Thread.sleep(4000);
        System.out.println("Finished : "+ i);
    }*/
}
