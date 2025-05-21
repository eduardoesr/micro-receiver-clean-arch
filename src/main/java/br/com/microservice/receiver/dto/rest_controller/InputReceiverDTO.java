package br.com.microservice.receiver.dto.rest_controller;

import br.com.microservice.receiver.domain.values_object.Endereco;
import br.com.microservice.receiver.domain.values_object.MetodoPagamento;
import br.com.microservice.receiver.domain.values_object.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InputReceiverDTO(
        String idCliente,
        LocalDateTime dataCriacao,
        List<Produto> produtos,
        Endereco enderecoEntrega,
        BigDecimal frete,
        MetodoPagamento metodoPagamento
) {
}