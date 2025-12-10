package br.com.curriculo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Curriculo {

    private String nome;
    private String email;
    private String telefone;
    private String cargoDesejado;
    private String resumoProfissional;
    private List<Experiencia> experiencias;
    private List<Formacao> formacoes;
    private List<String> habilidades;
}
