package com.jrcesar4.batchimportjson.repository;

import com.jrcesar4.batchimportjson.entity.RepresentanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepresentantRepository extends JpaRepository<RepresentanteEntity, Long> {
}
