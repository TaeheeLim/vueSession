package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.entity.project.CompilerFile;
import com.kanboo.www.domain.entity.project.Issue;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.CompilerContentRepository;
import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.domain.repository.project.ProjectMemberRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.member.ProjectMemberDTOInter;
import com.kanboo.www.dto.member.nativedto.ProjectMemberNative;
import com.kanboo.www.dto.project.IssueDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.project.CompilerContentService;
import com.kanboo.www.service.inter.project.ProjectService;
import com.kanboo.www.util.FileSystemUtil;
import com.kanboo.www.util.SaveCompileFile;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    private final MemberRepository memberRepository;
    private final CompilerContentRepository compilerContentRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Project save(ProjectDTO project) {
        return projectRepository.save(project.dtoToEntity());
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

    @Override
    public void saveProject(Map<String, Object> param) {
        /** 파라미터 조회 */
        ProjectDTO project = (ProjectDTO) param.get("project");
        String tag = param.get("tag") + "";

        /** 저장 */
        Member member = memberRepository.findByMemTag(tag);
        Project saveProject = projectRepository.save(project.dtoToEntity());

        Compiler compiler = Compiler.builder()
                .comNm("src")
                .comSe("d")
                .project(saveProject)
                .build();
        Compiler savedCompiler = compilerRepository.save(compiler);
        System.out.println(savedCompiler.entityToDto());

        Compiler mainFile = Compiler.builder()
                .comNm("Main.java")
                .comSe("f")
                .project(saveProject)
                .parentComIdx(savedCompiler.getComIdx())
                .build();
        Compiler savedMainFile = compilerRepository.save(mainFile);
        System.out.println(savedMainFile.entityToDto());

        StringBuilder source = new StringBuilder();
        source.append("public class Main {\n");
        source.append("\tpublic void main(String[] args) {\n");
        source.append("\t\tSystem.out.println(\"Hello World!\")");
        source.append("\t}");
        source.append("}");

        CompilerFile mainSource = CompilerFile.builder()
                .comFileCn(source.toString())
                .compiler(savedMainFile)
                .build();

        CompilerFile save1 = compilerContentRepository.save(mainSource);
        System.out.println(save1.entityToDto());

        ProjectMember pm = ProjectMember.builder()
                .project(saveProject)
                .member(member)
                .prjctMemRole("PM")
                .build();
        ProjectMember save = projectMemberRepository.save(pm);

        String successYn = "N";
        if(save != null) {
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
                param.put("project", saveProject.entityToDto());
                successYn = "Y";
            }
        }
        param.put("successYn",successYn);
    }

    @Override
    public ProjectDTO getDashBoardData(String memTag, Long projectIdx) {
        return projectRepository.getDashBoard(memTag, projectIdx);
    }
}
