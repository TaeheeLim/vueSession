package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.CompilerContentDTO;
import com.kanboo.www.dto.project.CompilerDTO;

public interface CompilerContentService {

    CompilerContentDTO getFile(CompilerDTO compilerDTO);

    boolean updateFile(CompilerContentDTO compilerContentDTO);
}
