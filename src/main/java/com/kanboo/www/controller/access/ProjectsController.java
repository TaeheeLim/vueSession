package com.kanboo.www.controller.access;

import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/pdtail")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

    @PostMapping("/createProject")
    public ProjectDTO createProject(@RequestParam Map<String, Object> map) {
        logger.info("hello");
        String prjctNm = map.get("prjctNm") + "";
        LocalDate startDate = LocalDate.parse(map.get("prjctStartDate") + "",
                DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(map.get("prjctEndDate") + "",
                DateTimeFormatter.ISO_DATE);

        ProjectDTO project = new ProjectDTO(null, prjctNm, startDate, endDate, 0, "N", "N");

        return projectService.save(project);
    }
}
