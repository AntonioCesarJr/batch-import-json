package com.jrcesar4.batchimportjson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysedDocument {

    private String razaoSocial;
    private String cnpj;
    private String orgaoRegistro;
    private List<PoderMapeado> poderesMapeados;
    private List<Socio> socios;
    private List<String> clausulasAtencao;
}
