package br.com.curriculo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Experiencia {
    private String empresa;
    private String cargo;
    private String periodo;
    private String descricao;

}
