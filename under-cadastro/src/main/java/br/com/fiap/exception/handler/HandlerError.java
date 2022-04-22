package br.com.fiap.exception.handler;

import br.com.fiap.exception.ApiException;
import br.com.fiap.exception.ErroDTO;
import br.com.fiap.exception.ErroFormDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class HandlerError {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroFormDTO> handle(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream().map(a ->
            new ErroFormDTO(a.getField(), a.getDefaultMessage())).collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroDTO> onErro(ApiException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDTO(exception.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroDTO> onErro(ResponseStatusException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDTO(exception.getReason()));
    }
}
