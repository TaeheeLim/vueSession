package com.kanboo.www.service.inter.project;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public interface CompilerService {

    Map<String, String> runDemo(String code);
}
