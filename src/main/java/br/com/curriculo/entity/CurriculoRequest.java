package br.com.curriculo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurriculoRequest {
    private String mensagem;
    private boolean sucesso;
    private String nomeArquivo;
}
