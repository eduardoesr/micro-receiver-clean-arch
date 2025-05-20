package br.com.microservice.receiver.controller;

import br.com.microservice.receiver.domain.values_object.Endereco;
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
        List<Produto> produtos = List.of(new Produto("123", 10), new Produto("456", 20));
        Endereco endereco = new Endereco("20", "Rua Teste", 10, 20);
        InputReceiverDTO input = new InputReceiverDTO(
                produtos,
                "123456789",
                "123456789",
                endereco,
                BigDecimal.valueOf(10.29)
        );
        // preencha os campos obrigatórios do DTO conforme necessário

        Mockito.when(useCase.forward(any(InputReceiverDTO.class))).thenReturn("ok");

        mockMvc.perform(post("/receiver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(content().string("ok"));
    }

//    @Test
//    void forward_deveRetornar400QuandoInputInvalido() throws Exception {
//        InputReceiverDTO input = new InputReceiverDTO(
//                null, // produtos inválido
//                null, // cnpj inválido
//                null, // inscricaoEstadual inválido
//                null, // endereco inválido
//                null  // valorTotal inválido
//        );
//        // NÃO preencha campos obrigatórios para forçar erro de validação
//
//        mockMvc.perform(post("/receiver")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }


}