package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public interface CompilerService {

    Map<String, String> runDemo(String code);

}
