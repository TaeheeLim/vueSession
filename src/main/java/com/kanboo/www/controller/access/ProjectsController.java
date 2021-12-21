package com.kanboo.www.controller.access;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.member.ProjectMemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.security.JwtSecurityService;
import com.kanboo.www.service.inter.project.ProjectService;
import com.kanboo.www.util.FileSystemUtil;
import com.kanboo.www.util.SaveCompileFile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pdtail")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;
    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
    private final FileSystemUtil fileSystemUtil;
    private final SaveCompileFile saveCompileFile;
    private final JwtSecurityService jwtSecurityService;

    @GetMapping("/allList")
    public Map<String, Object> getAllList(String token) {
        return projectService.getAllList(token);
    }

    @PostMapping("/createProject")
    public ProjectDTO createProject(@RequestParam Map<String, Object> map) {
        /** 파라미터 조회 */
        String token = map.get("token") + "";
        String tag = jwtSecurityService.getToken(token);
        if(tag == null) {
            return null;
        }

        String prjctNm = map.get("prjctNm") + "";
        LocalDate startDate = LocalDate.parse(map.get("prjctStartDate") + "", DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(map.get("prjctEndDate") + "", DateTimeFormatter.ISO_DATE);

        logger.info("createProject{} - 파라미터 조회 ::: startDate = {}, endDate = {} ", prjctNm, startDate, endDate);

        /** 검증 */

        /** 서비스 파라미터 생성 */
        Map<String,Object> param = new HashMap<>();
        ProjectDTO project = ProjectDTO.builder()
                    .prjctNm(prjctNm)
                    .prjctStartDate(startDate)
                    .prjctEndDate(endDate)
                    .prjctComplAt("n")
                    .prjctProgress(0)
                    .prjctDelAt("n")
                    .prjctReadMe("")
                    .build();
        param.put("project",project);
        param.put("tag", tag);

        /** 서비스 호출 2개 (트랜잭션 고려)
         *
         */
        projectService.saveProject(param);
        logger.info("createProject - 파라미터 조회 ::: 서비스 호출 완료");

        /** Client 반환(파라미터 구성, 화면설정) */
        String successYn = (String) param.get("successYn");
        logger.info("createProject{" + prjctNm + "} - 파라미터 조회 ::: successYn = {+" + successYn +"+}");
        if("Y".equals(successYn)){
            ProjectDTO returnProject = (ProjectDTO) param.get("project");
            return returnProject;
        }
        return null;

        // logfile : server.log
        // tail -f server.log | grep createProject12345
        // tail -f server.log | grep -A 10 -B 10 Exception
    }
}


