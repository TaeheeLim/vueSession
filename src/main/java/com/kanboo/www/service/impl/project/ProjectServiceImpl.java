package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.entity.project.Issue;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.domain.repository.project.ProjectMemberRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.project.ProjectService;
import com.kanboo.www.util.FileSystemUtil;
import com.kanboo.www.util.SaveCompileFile;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FileSystemUtil fileSystemUtil;
    private final CompilerRepository compilerRepository;
    private final SaveCompileFile saveCompileFile;
    private final JwtSecurityService jwtSecurityService;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public ProjectDTO save(ProjectDTO project) {
        Project save = projectRepository.save(project.dtoToEntity());
        return save.entityToDto();
    }

    @Override
    @Transactional
    public void updateReadMe(ProjectDTO project) {
        Project prjtc = projectRepository.findById(project.getPrjctIdx()).get();
        prjtc.updateReadMe(project.getPrjctReadMe());
    }

    @Override
    public ProjectDTO getProject(Long projectIdx) {
        Project project = projectRepository.findByPrjctIdx(projectIdx);
        ProjectDTO projectDTO = project.entityToDto();
        project.getProjectMembers().forEach(item -> {
            projectDTO.getProjectMembers().add(item.entityToDto());
        });

        return projectDTO;
    }

    @Override
    public boolean addDirOrFile(Map<String, Object> map) {

        String type = map.get("type") + "";
        Long projectIdx = Long.parseLong(map.get("projectIdx") + "");

        Project project = projectRepository.findByPrjctIdx(projectIdx);

        String name = map.get("name") + "";
        String path = map.get("path") + "";
        String classification = map.get("classification") + "";

        boolean result = false;

        if(type.equals("Directory")) {
            Map<String, String> rootPath = new HashMap<>();
            rootPath.put("rootPath", "/compileFiles/member");
            rootPath.put("project", project.getPrjctIdx() + project.getPrjctNm());
            rootPath.put("path", "project");
            rootPath.put("name", path + "/" + name);
            rootPath.put("type", "dir");
            result = fileSystemUtil.createFileOrDir(rootPath);
        } else {
            Map<String, String> rootPath = new HashMap<>();
            rootPath.put("filePath", path);
            rootPath.put("fileName", name);
            rootPath.put("fileExtension", classification);
            rootPath.put("fileDetail", "");
            rootPath.put("project", "/member/" + project.getPrjctIdx() + project.getPrjctNm() + "/project");
            rootPath.put("type", "file");
            result = saveCompileFile.saveFile(rootPath);
        }

        if(result) {
            Compiler parentCompiler = compilerRepository.findByProjectPrjctIdxAndComNm(projectIdx, path);

            Compiler compiler = Compiler.builder()
                    .project(project)
                    .parentComIdx(parentCompiler.getComIdx())
                    .comSe(classification.substring(0, 1))
                    .comNm(name)
                    .build();

            result = compilerRepository.save(compiler) != null;
        }

        return result;
    }

    @Override
    public Map<String, Object> getAllList(String token) {
        String exeToken = jwtSecurityService.getToken(token);
        if(!exeToken.isEmpty()) {
            Map<String, Object> resultMap = new HashMap<>();

            List<ProjectMember> allList = projectMemberRepository.getAllList(exeToken);
            List<ProjectMemberDTO> list = new ArrayList<>();
            allList.forEach(item -> {
                list.add(item.entityToDto());
            });

            resultMap.put("projectMemberDtoList", list);

            return resultMap;
        }
        return null;
    }
}
