package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.CompilerDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.CompilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compiler")
public class CompilerController {

    private final CompilerService compilerService;

    @PostMapping("/getData")
    public List<CompilerDTO> getCompiler(@RequestBody ProjectDTO projectDTO) {
        if(projectDTO.getPrjctIdx() == null) {
            return null;
        }
        return compilerService.getList(projectDTO);
    }

}
