package br.com.microservice.receiver.controller;

import br.com.microservice.receiver.dto.rest_controller.InputReceiverDTO;
import br.com.microservice.receiver.usecase.ReceiverUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receiver")
@Tag(name = "Receiver", description = "Recebe o pedido, valida os itens enviados e repassa para a fila.")
public class ReceiverController {
    final ReceiverUseCase useCase;

    public ReceiverController(ReceiverUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @Operation(
            summary = "Recebe e repassa um pedido"
    )
    public ResponseEntity<String> forward(@Valid @RequestBody InputReceiverDTO input) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.forward(input));
    }
}
