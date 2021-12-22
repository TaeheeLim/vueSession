package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.ProjectDTO;

import java.util.Map;

public interface DashBoardService {
    ProjectDTO getDashBoard(Map<String, String> map);
}
