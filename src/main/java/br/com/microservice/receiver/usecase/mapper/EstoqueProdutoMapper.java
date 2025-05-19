//package br.com.microservice.receiver.usecase.mapper;
//
//import br.com.microservice.receiver.domain.EstoqueProduto;
//import br.com.microservice.receiver.dto.ReceiverDTO;
//
//public class EstoqueProdutoMapper {
//    public static EstoqueProduto mapToDomain(ReceiverDTO dto) {
//        return EstoqueProduto.reconstituir(
//                dto.id(),
//                dto.sku(),
//                dto.quantidade()
//        );
//    }
//
//    public static ReceiverDTO mapToDTO(EstoqueProduto estoqueProduto) {
//        return new ReceiverDTO(
//                estoqueProduto.getId(),
//                estoqueProduto.getSku(),
//                estoqueProduto.getQuantidade()
//        );
//    }
//}
