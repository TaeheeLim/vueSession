package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.Compiler;
import com.kanboo.www.domain.entity.project.CompilerFile;
import com.kanboo.www.domain.entity.project.QCompilerFile;
import com.kanboo.www.domain.repository.project.CompilerContentRepository;
import com.kanboo.www.domain.repository.project.CompilerRepository;
import com.kanboo.www.dto.project.CompilerContentDTO;
import com.kanboo.www.dto.project.CompilerDTO;
import com.kanboo.www.service.inter.project.CompilerContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompilerContentServiceImpl implements CompilerContentService {

    private final CompilerContentRepository compilerContentRepository;
    private final CompilerRepository compilerRepository;

    public CompilerContentDTO getFile(CompilerDTO compilerDTO) {
        return compilerContentRepository.findByCompiler_ComIdx(compilerDTO.getComIdx()).entityToDto();
    }

    public boolean updateFile(CompilerContentDTO compilerContentDTO) {
        Compiler compiler = compilerRepository.findByComIdx(compilerContentDTO.getCompiler().getComIdx());
        CompilerFile file = CompilerFile.builder()
                .comFileIdx(compilerContentDTO.getComFileIdx())
                .compiler(compiler)
                .comFileCn(compilerContentDTO.getComFileCn())
                .build();
        CompilerFile save = compilerContentRepository.save(file);
        if(save.getComFileIdx() != null) {
            return true;
        }
        return false;
    }
}
