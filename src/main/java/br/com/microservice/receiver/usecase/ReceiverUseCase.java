package br.com.microservice.receiver.usecase;

import br.com.microservice.receiver.dto.rest_controller.InputReceiverDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceiverUseCase {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public ReceiverUseCase(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // ReceiverDTO
    public String forward(InputReceiverDTO receiverDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(receiverDTO);
            String queue = "PedidoQueue";
            rabbitTemplate.convertAndSend(queue, message);
            return "Mensagem enviada com sucesso!";
        } catch (JsonProcessingException e) {
            return "Erro ao enviar a mensagem.";
        }
    }
}
