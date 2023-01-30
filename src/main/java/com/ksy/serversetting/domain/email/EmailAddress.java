package com.ksy.serversetting.domain.email;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmailAddress {

    private static final String AT = "@";

    private String name;
    private String domainPart;
    private String localPart;

    public EmailAddress(String name, String localPart, String domainPart) {
        this.name = name;
        this.domainPart = domainPart;
        this.localPart = localPart;
    }


}
