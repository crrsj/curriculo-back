package br.com.curriculo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Formacao {
    private String curso;
    private String instituicao;
    private String periodo;
    private String anoConclusao;
}
