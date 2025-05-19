package br.com.microservice.receiver.domain.values_object;

public record Produto(
    String sku,
    Integer quantidade
) {}
