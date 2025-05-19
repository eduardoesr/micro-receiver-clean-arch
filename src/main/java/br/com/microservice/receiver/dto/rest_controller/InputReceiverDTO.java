package br.com.microservice.receiver.dto.rest_controller;

import br.com.microservice.receiver.domain.values_object.Endereco;
import br.com.microservice.receiver.domain.values_object.Produto;

import java.math.BigDecimal;
import java.util.List;

public record InputReceiverDTO(
        List<Produto> produtos,
        String cpfCliente,
        String pagamento,
        Endereco endereco,
        BigDecimal frete
) {
}