package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectDTO save(ProjectDTO project) {
        Project save = projectRepository.save(project.dtoToEntity());
        return save.entityToDto();
    }
}
