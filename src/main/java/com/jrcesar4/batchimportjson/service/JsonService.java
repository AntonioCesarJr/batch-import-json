package com.jrcesar4.batchimportjson.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrcesar4.batchimportjson.dto.AnalysedDocument;
import com.jrcesar4.batchimportjson.dto.PoderMapeado;
import com.jrcesar4.batchimportjson.dto.Root;
import com.jrcesar4.batchimportjson.dto.Socio;
import com.jrcesar4.batchimportjson.entity.EmpresaEntity;
import com.jrcesar4.batchimportjson.entity.PoderEntity;
import com.jrcesar4.batchimportjson.entity.RepresentanteEntity;
import com.jrcesar4.batchimportjson.entity.RestricaoEntity;
import com.jrcesar4.batchimportjson.repository.EmpresaRepository;
import com.jrcesar4.batchimportjson.repository.PoderRepository;
import com.jrcesar4.batchimportjson.repository.RepresentantRepository;
import com.jrcesar4.batchimportjson.repository.RestricaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class JsonService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    RepresentantRepository representantRepository;

    @Autowired
    PoderRepository poderRepository;

    @Autowired
    RestricaoRepository restricaoRepository;

    public String readJsonFile(String path){
        String content = new String();
        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            log.error(path + " -> " + e.getMessage());
            return null;
        }
        return content;
    }

    public Root parseJson(String json){
        if (json == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Root root = new Root();
        try {
            root = objectMapper.readValue(json, Root.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return root;
    }

    public void saveData(Root root){
        EmpresaEntity empresaEntity = saveEmpresa(root);
        if (empresaEntity != null){
            savePoderes(root, empresaEntity);
            saveRepresentants(root, empresaEntity);
            saveRestricoes(root, empresaEntity);
            log.info(empresaEntity.toString());
        }
    }

    private void saveRestricoes(Root root, EmpresaEntity empresaEntity) {
        AnalysedDocument analysedDocument = root.getAnalysedDocuments().get(0);
        for(String clausula: analysedDocument.getClausulasAtencao()){
            RestricaoEntity restricaoEntity = RestricaoEntity.builder()
                    .texto(clausula)
                    .empresa(empresaEntity)
                    .build();
            try{
                restricaoRepository.save(restricaoEntity);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    private void saveRepresentants(Root root, EmpresaEntity empresaEntity) {
        AnalysedDocument analysedDocument = root.getAnalysedDocuments().get(0);
        for(Socio socio: analysedDocument.getSocios()){
            RepresentanteEntity representanteEntity = RepresentanteEntity
                    .builder()
                    .nome(socio.getNome())
                    .cpf(socio.getCpf())
                    .empresa(empresaEntity)
                    .build();
            try{
                representantRepository.save(representanteEntity);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    private void savePoderes(Root root, EmpresaEntity empresaEntity) {
        AnalysedDocument analysedDocument = root.getAnalysedDocuments().get(0);
        for(PoderMapeado poder: analysedDocument.getPoderesMapeados()){
            PoderEntity poderEntity = PoderEntity.builder()
                    .texto(poder.getPoder())
                    .empresa(empresaEntity)
                    .build();
            try{
                poderRepository.save(poderEntity);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    private EmpresaEntity saveEmpresa(Root root) {
        if (!root.getAnalysedDocuments().isEmpty()){
            AnalysedDocument analysedDocument = root.getAnalysedDocuments().get(0);
            EmpresaEntity empresaEntity = EmpresaEntity.builder()
                    .orgaoRegistro(analysedDocument.getOrgaoRegistro())
                    .razaoSocial(analysedDocument.getRazaoSocial())
                    .cnpj(analysedDocument.getCnpj())
                    .build();
            try{
                return empresaRepository.save(empresaEntity);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return null;
    }
}
