package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.dto.project.CompilerDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.CompilerContentService;
import com.kanboo.www.service.inter.project.CompilerService;
import com.kanboo.www.util.CompilerUtil;
import com.kanboo.www.util.SaveCompileFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompilerServiceImpl implements CompilerService {

    private final SaveCompileFile saveCompileFile;
    private final CompilerUtil compilerUtil;
    private static final String rootPath = "./compileFiles/";
    private final CompilerRepository compilerRepository;

    @Override
    public Map<String, String> runDemo(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("filePath", "/src/");
        map.put("fileName", "Main");
        map.put("fileExtension", ".java");
        map.put("fileDetail", code);
        map.put("project", "demo/project");
        boolean saveResult = saveCompileFile.saveFile(map);

        if(!saveResult) {
            return null;
        }

        Map<String, String> pathList = new HashMap<>();
        pathList.put("classPath", rootPath + "demo/project/class");
        pathList.put("srcPath", rootPath + "demo/project/src");
        pathList.put("jarNameAndPath", rootPath + "demo/project/lib/result.jar");
        pathList.put("manifestNameAndPath", rootPath + "demo/project/META-INF/Manifest.txt");
        pathList.put("topPath", rootPath + "demo/project");

        Map<String, String[]> cmdList = compilerUtil.createCmd(pathList);

        Map<String, String> compileCmd = compilerUtil.terminalCompile(cmdList.get("compileCmd"));
        if(compileCmd.get("isSuccess").equals("false")) {
            compileCmd.put("detail", compileCmd.get("detail").substring(32, compileCmd.get("detail").length()));
            return compileCmd;
        }

        compilerUtil.terminalCompile(cmdList.get("createManifestCmd"));

        compilerUtil.terminalCompile(cmdList.get("setJarCmd"));

        compilerUtil.terminalCompile(cmdList.get("setManifestCmd"));

        return compilerUtil.terminalCompile(cmdList.get("runJarCmd"));
    }

    @Override
    public List<CompilerDTO> getList(ProjectDTO projectDTO) {
        List<Compiler> projectEntity = compilerRepository.findByProjectPrjctIdx(projectDTO.getPrjctIdx());
        List<CompilerDTO> projectList = new ArrayList<>();
        projectEntity.forEach(item -> {
            projectList.add(CompilerDTO.builder()
                    .comIdx(item.getComIdx())
                    .comNm(item.getComNm())
                    .comSe(item.getComSe())
                    .parentComIdx(item.getParentComIdx())
                    .build());
        });
        return projectList;
    }


}
