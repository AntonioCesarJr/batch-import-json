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
public class Root {

    private List<AnalysedDocument> analysedDocuments;

}
