package com.kanboo.www.domain.entity.project;

import com.kanboo.www.dto.project.ErdDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Erd {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long erdIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prjct_idx")
    private Project project;
    private String erdName;
    private int erdOrder;

    public ErdDTO entityToDto() {
        return ErdDTO.builder()
                .erdIdx(erdIdx)
                .project(project.entityToDto())
                .erdName(erdName)
                .erdOrder(erdOrder)
                .build();
    }
}
