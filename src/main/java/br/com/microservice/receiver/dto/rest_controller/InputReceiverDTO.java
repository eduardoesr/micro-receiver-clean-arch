package br.com.microservice.receiver.dto.rest_controller;

import br.com.microservice.receiver.domain.values_object.Endereco;
import br.com.microservice.receiver.domain.values_object.MetodoPagamento;
import br.com.microservice.receiver.domain.values_object.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public record InputReceiverDTO(
        String clienteId,
        LocalDateTime dataCriacao,
        HashMap<String, Integer> produtos,
        Endereco enderecoEntrega,
        BigDecimal frete,
        MetodoPagamento metodoPagamento
) {
}