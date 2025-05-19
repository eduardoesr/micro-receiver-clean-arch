package br.com.microservice.receiver.domain.values_object;

public record Endereco (
        String cep,
        String enderecoCompleto,
        Integer latitude,
        Integer longitudade
)
{
}
