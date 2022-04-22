package br.com.fiap.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroFormDTO {
    private String campo;
    private String erro;
}
