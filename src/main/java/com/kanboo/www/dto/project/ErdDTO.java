package com.kanboo.www.dto.project;

import com.kanboo.www.domain.entity.project.Erd;
import com.kanboo.www.domain.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErdDTO {

    private Long erdIdx;
    private ProjectDTO project;
    private String erdName;
    private int erdOrder;

    public Erd dtoToEntity() {
        return Erd.builder()
                .erdIdx(erdIdx)
                .project(project.dtoToEntity())
                .erdName(erdName)
                .erdOrder(erdOrder)
                .build();
    }
}
