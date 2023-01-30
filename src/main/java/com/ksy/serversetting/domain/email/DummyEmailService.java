package com.ksy.serversetting.domain.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!email")
public class DummyEmailService implements EmailService{

    @Override
    public boolean sendEmail(EmailAddress emailAddress) {
        System.out.println("dummy email... "  );
        return true;
    }
}
