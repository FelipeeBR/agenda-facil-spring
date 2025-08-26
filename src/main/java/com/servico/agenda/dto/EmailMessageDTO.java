package com.servico.agenda.dto;

import java.io.Serializable;

public class EmailMessageDTO implements Serializable {
    public String to;
    public String subject;
    public String text;

    public EmailMessageDTO(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public EmailMessageDTO() {}

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
