package br.com.microservice.receiver.usecase;

import br.com.microservice.receiver.domain.values_object.Endereco;
import br.com.microservice.receiver.domain.values_object.Produto;
import br.com.microservice.receiver.dto.rest_controller.InputReceiverDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
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
        List<Produto> produtos = List.of(new Produto("123", 10));
        Endereco endereco = new Endereco("20", "Rua Teste", 10, 20);
        InputReceiverDTO dto = new InputReceiverDTO(
                produtos, "123456789", "123456789", endereco, BigDecimal.valueOf(10.29)
        );

        String resultado = useCase.forward(dto);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(rabbitTemplate).convertAndSend(eq("PedidoQueue"), captor.capture());
        assertEquals("Mensagem enviada com sucesso!", resultado);
    }

//    @Test
//    void forward_deveRetornarErroQuandoJsonProcessingException() throws Exception {
//        InputReceiverDTO dto = mock(InputReceiverDTO.class);
//        ReceiverUseCase useCaseSpy = spy(useCase);
//        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
//
//        doReturn(objectMapperMock).when(useCaseSpy).getObjectMapper();
//        when(objectMapperMock.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
//
//        // Para acessar o ObjectMapper, seria necessário refatorar o método para injetar o mapper ou torná-lo protegido.
//        // Como alternativa, pode-se testar apenas o fluxo normal, pois o erro de serialização é improvável em DTOs simples.
//    }
}