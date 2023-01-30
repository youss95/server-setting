package com.ksy.serversetting.domain.email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(path = "/hotels/email/test")
    public ResponseEntity<Void> sendByEmailTest() {
        emailService.sendEmail(new EmailAddress("abc","cdf","kkk"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
