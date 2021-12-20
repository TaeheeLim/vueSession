package com.kanboo.www.service.inter.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    ProjectDTO save(ProjectDTO project);

    void updateReadMe(ProjectDTO project);

    ProjectDTO getProject(Long projectIdx);

    boolean addDirOrFile(Map<String, Object> map);

    Map<String, Object> getAllList(String token);
}
