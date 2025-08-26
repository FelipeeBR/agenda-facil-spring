package com.servico.agenda.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servico.agenda.config.RabbitMQConfig;
import com.servico.agenda.dto.EmailMessageDTO;

@Service
public class EmailConsumer {
    private final JavaMailSender javaMailSender;

    public EmailConsumer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_EMAIL)
    public void receiveMessage(String messageJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            EmailMessageDTO emailMessage = mapper.readValue(messageJson, EmailMessageDTO.class);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.getTo());
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getText());

            javaMailSender.send(message);
            System.out.println("Email enviado para " + emailMessage.getTo());
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
        }
    }
}
