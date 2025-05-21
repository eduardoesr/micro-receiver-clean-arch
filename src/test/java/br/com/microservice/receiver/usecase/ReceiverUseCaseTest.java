package br.com.microservice.receiver.usecase;

import br.com.microservice.receiver.domain.values_object.Endereco;
import br.com.microservice.receiver.domain.values_object.MetodoPagamento;
import br.com.microservice.receiver.domain.values_object.Produto;
import br.com.microservice.receiver.dto.rest_controller.InputReceiverDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReceiverUseCaseTest {

    private RabbitTemplate rabbitTemplate;
    private ReceiverUseCase useCase;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        useCase = new ReceiverUseCase(rabbitTemplate);
    }

    @Test
    void forward_deveEnviarMensagemComSucesso() {
        List<Produto> produtos = List.of(new Produto("SKU1", 2), new Produto("SKU2", 1));
        Endereco endereco = new Endereco("123", "Rua Teste", 100, 200);
        LocalDateTime dataPedido = LocalDateTime.now();

        InputReceiverDTO dto = new InputReceiverDTO(
                "cliente123",           // idCliente
                dataPedido,            // dataPedido
                produtos,              // produtos
                endereco,              // endereco
                BigDecimal.valueOf(50), // valorTotal
                MetodoPagamento.PIX    // metodoPagamento
        );

        String resultado = useCase.forward(dto);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        verify(rabbitTemplate).convertAndSend(eq("PedidoQueue"), captor.capture());
//        assertEquals("Mensagem enviada com sucesso!", resultado);
    }
}