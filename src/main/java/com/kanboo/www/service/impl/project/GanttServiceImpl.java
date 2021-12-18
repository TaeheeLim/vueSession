package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Gantt;
import com.kanboo.www.domain.repository.project.GanttRepository;
import com.kanboo.www.dto.project.GanttDTO;
import com.kanboo.www.service.inter.project.GanttService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GanttServiceImpl implements GanttService {

    private final GanttRepository ganttRepository;

    @Override
    public List<GanttDTO> getGantt(Long projectIdx) {
        List<Gantt> ganttList = ganttRepository.findByProjectPrjctIdx(projectIdx);
        List<GanttDTO> gantt = new ArrayList<>();

        ganttList.forEach(item -> {
            gantt.add(item.entityToDto());
        });

        return gantt;
    }
}
