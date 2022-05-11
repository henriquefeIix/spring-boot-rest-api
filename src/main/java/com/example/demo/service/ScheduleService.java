package com.example.demo.service;

import javax.mail.MessagingException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final LoanService loanService;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 13 1/1 * ?")
    public void scheduleReminder() throws MessagingException {
        this.emailService.sendEmails(this.loanService.findOverdueLoans());
    }

}
