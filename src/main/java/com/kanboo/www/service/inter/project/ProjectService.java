package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.ProjectDTO;

import java.util.Map;

public interface ProjectService {
    ProjectDTO save(ProjectDTO project);

    void updateReadMe(ProjectDTO project);

    ProjectDTO getProject(Long projectIdx);

    boolean addDirOrFile(Map<String, Object> map);
}
