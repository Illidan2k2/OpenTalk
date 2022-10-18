package com.example.ncc;

import com.example.ncc.controller.BranchController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NccApplication {
    /*private static String name;

    @Value("${title}")
    public void setTitle(String title) {
        name = title;
    }*/

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NccApplication.class, args);
        BranchController brandController = context.getBean(BranchController.class);
        context.getBeanFactory().destroyBean(brandController);
    }

    /*@Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println(name);
        };
    }*/
}
