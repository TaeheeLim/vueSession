package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/getList")
    public ProjectDTO getDashBoard(@RequestParam Map<String, String> map) {
        return dashBoardService.getDashBoard(map);
    }
}
