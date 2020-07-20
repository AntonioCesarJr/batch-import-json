package com.jrcesar4.batchimportjson.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="Empresa")
@Table(name= "empresa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @JsonIgnore
    private Long id;

//    @NotEmpty
//    @Column(nullable = false)
    private String razaoSocial;

//    @NotEmpty
//    @Column(nullable = false)
    private String cnpj;

    private String orgaoRegistro;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    private List<PoderEntity> poderes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    private List<RepresentanteEntity> representantes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    private List<RestricaoEntity> restricoes;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime updatedAt;

}
