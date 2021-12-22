package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.member.MemberService;
import com.kanboo.www.service.inter.project.DashBoardService;
import com.kanboo.www.service.inter.project.GanttService;
import com.kanboo.www.service.inter.project.IssueService;
import com.kanboo.www.service.inter.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {

    private final JwtSecurityService jwtSecurityService;
    private final ProjectService projectService;

    @Override
    public ProjectDTO getDashBoard(Map<String, String> map) {
//        String token = map.get("token");
//        Long projectIdx = map.get("projectIdx") != null ? Long.parseLong(map.get("projectIdx")) : null;
//
//        String memTag = jwtSecurityService.getToken(token);
//        if(memTag != null && projectIdx != null) {
//            return projectService.getDashBoardData(memTag, projectIdx);
//        }
//        return null;
        return projectService.getDashBoardData("#B3Uwm", 1L);
    }
}
