package com.jrcesar4.batchimportjson.repository;

import com.jrcesar4.batchimportjson.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {

}
