package com.techprimers.security.securitydbexample.resource;

import com.techprimers.security.securitydbexample.config.MailSenderCfg;
import com.techprimers.security.securitydbexample.model.MailFeedback;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private MailSenderCfg mailSenderCfg;

    public FeedbackController(MailSenderCfg emailCfg) {
        this.mailSenderCfg = emailCfg;
    }

    @PostMapping
    public void sendFeedback(@RequestBody MailFeedback feedback,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Feedback is not valid");
        } // if hasErrors

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailSenderCfg.getHost());
        mailSender.setPort(this.mailSenderCfg.getPort());
        mailSender.setUsername(this.mailSenderCfg.getUsername());
        mailSender.setPassword(this.mailSenderCfg.getPassword());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedback.getEmail());
        mailMessage.setTo("wulongcs@gmail.com");
        mailMessage.setSubject("New feedback from " + feedback.getName());
        //mailMessage.setText(feedback.getFeedback());
        mailMessage.setText("Testando\n%s\nMa oi!!!\n%s\n É bom ou não é?!");

        mailSender.send((SimpleMailMessage) mailMessage);
    } // sendFeedback
} // FeedbackController
