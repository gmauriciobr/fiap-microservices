package br.com.fiap.exception;

public class ApiException extends RuntimeException {
    public ApiException(String erro) {
        super(erro);
    }
}
