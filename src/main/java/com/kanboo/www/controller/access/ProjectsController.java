package com.kanboo.www.controller.access;

import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.ProjectService;
import com.kanboo.www.util.FileSystemUtil;
import com.kanboo.www.util.SaveCompileFile;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdtail")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
    private final FileSystemUtil fileSystemUtil;
    private final SaveCompileFile saveCompileFile;

    @PostMapping("/createProject")
    public ProjectDTO createProject(@RequestParam Map<String, Object> map) {
        logger.info("hello");
        String prjctNm = map.get("prjctNm") + "";
        LocalDate startDate = LocalDate.parse(map.get("prjctStartDate") + "",
                DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(map.get("prjctEndDate") + "",
                DateTimeFormatter.ISO_DATE);

        ProjectDTO project = new ProjectDTO(null, prjctNm, startDate, endDate, 0, "N", "N", null, null);

        ProjectDTO saveProject = projectService.save(project);

        if(saveProject != null) {
            Map<String, String> creatDirPath = new HashMap<>();
            creatDirPath.put("rootPath", "/compileFiles/member");
            creatDirPath.put("project", saveProject.getPrjctIdx() + saveProject.getPrjctNm());
            creatDirPath.put("path", "project");
            creatDirPath.put("name", "src");
            creatDirPath.put("type", "dir");
            fileSystemUtil.createFileOrDir(creatDirPath);

            creatDirPath.put("name", "class");
            fileSystemUtil.createFileOrDir(creatDirPath);

            creatDirPath.put("name", "META-INF");
            fileSystemUtil.createFileOrDir(creatDirPath);

            creatDirPath.put("name", "lib");
            fileSystemUtil.createFileOrDir(creatDirPath);

            Map<String, String> createFile = new HashMap<>();
            StringBuilder manifest = new StringBuilder();
            manifest.append("Class-Path: ../class/\n");
            manifest.append("Main-Class: Main");

            createFile.put("filePath", "/META-INF/");
            createFile.put("fileName", "Manifest");
            createFile.put("fileExtension", ".text");
            createFile.put("fileDetail", manifest.toString());
            createFile.put("project", "/member/" + saveProject.getPrjctIdx() + saveProject.getPrjctNm() + "/project");
            saveCompileFile.saveFile(createFile);

            StringBuilder main = new StringBuilder();
            main.append("public class Main {\n");
            main.append("   public static void main(String[] args) {\n");
            main.append("       \n");
            main.append("   }\n");
            main.append("}");

            createFile.put("filePath", "/src/");
            createFile.put("fileName", "Main");
            createFile.put("fileExtension", ".java");
            createFile.put("fileDetail", main.toString());
            boolean isSave = saveCompileFile.saveFile(createFile);
            if(isSave) {
                return project;
            }
        }

        return null;
    }
}
