package br.com.microservice.receiver.usecase;

import br.com.microservice.receiver.dto.rest_controller.InputReceiverDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceiverUseCase {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ReceiverUseCase(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public String forward(InputReceiverDTO receiverDTO) {
        try {
            String message = objectMapper.writeValueAsString(receiverDTO);
            String queue = "PedidoQueue";
            rabbitTemplate.convertAndSend(queue, message);
            return "Mensagem enviada com sucesso!";
        } catch (JsonProcessingException e) {
            log.info("Erro ao transformar a mensagem em JSON: {}.", e.getMessage());
            return "Houve um erro ao criar o pedido.";
        }
    }
}