package br.com.microservice.receiver.controller;

import br.com.microservice.receiver.domain.values_object.Endereco;
import br.com.microservice.receiver.domain.values_object.MetodoPagamento;
import br.com.microservice.receiver.domain.values_object.Produto;
import br.com.microservice.receiver.dto.rest_controller.InputReceiverDTO;
import br.com.microservice.receiver.usecase.ReceiverUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiverController.class)
class ReceiverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReceiverUseCase useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void forward_deveRetornar201ComBodyQuandoInputValido() throws Exception {
        HashMap<String, Integer> produtos = new HashMap<>();
        produtos.put("SKU1", 2);
        produtos.put("SKU2", 1);
        Endereco endereco = new Endereco("123", "Rua Teste", 100, 200);
        LocalDateTime dataPedido = LocalDateTime.now();

        InputReceiverDTO input = new InputReceiverDTO(
                "cliente123",           // idCliente
                dataPedido,            // dataPedido
                produtos,              // produtos
                endereco,              // endereco
                BigDecimal.valueOf(50), // valorTotal
                MetodoPagamento.PIX    // metodoPagamento
        );

        Mockito.when(useCase.forward(any(InputReceiverDTO.class))).thenReturn("ok");

        mockMvc.perform(post("/receiver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(content().string("ok"));
    }

}