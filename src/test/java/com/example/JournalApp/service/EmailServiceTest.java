package com.example.JournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testEmailSender(){
        emailService.sendEmail("fortestwork25@gmail.com" ,
                "java mail sender test" ,
                "Hi this is from EmailServiceTest");
    }

}
