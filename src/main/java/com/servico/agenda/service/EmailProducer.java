package com.servico.agenda.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servico.agenda.config.RabbitMQConfig;
import com.servico.agenda.dto.EmailMessageDTO;

@Service
public class EmailProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public EmailProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            EmailMessageDTO dto = new EmailMessageDTO(to, subject, text);
            String json = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_EMAIL,
                    RabbitMQConfig.ROUTING_KEY_EMAIL,
                    json
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para a fila", e);
        }
    }
}
