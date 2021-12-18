package com.kanboo.www.controller.access;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.member.BanDTO;
import com.kanboo.www.dto.member.MemberDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.board.BoardService;
import com.kanboo.www.service.inter.member.MemberService;
import com.kanboo.www.service.inter.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ProjectService projectService;

    @GetMapping("/getAllMember")
    public List<MemberDTO> getMemList(){
//        List<MemberDTO> memberList = memberService.getAllMember();
//        List<MemberDTO> returnList = new ArrayList<>();
//        for (MemberDTO dto : memberList) {
//            MemberDTO memberDTO = new MemberDTO();
//            memberDTO.setMemId( dto.getMemId() );
//            if( dto.getBan() != null){
//
//
//            memberDTO.setBan( dto.getBan() );
//            memberDTO.getBan().setBanStartDate( dto.getBan().getBanStartDate() );
//            memberDTO.getBan().setBanEndDate( dto.getBan().getBanEndDate() );
//            }
//            memberDTO.setMemTag( dto.getMemTag() );
//            returnList.add(memberDTO);
//        }
//        return returnList;
        return memberService.getAllMember();
    }

//    public List<ProjectDTO> getProjectList(){
//        return memberService.getAllProject();
//    }

}
