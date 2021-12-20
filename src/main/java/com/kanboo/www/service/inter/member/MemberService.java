package com.kanboo.www.service.inter.member;

import com.kanboo.www.dto.member.MemberDTO;

import java.util.List;

public interface MemberService {

    MemberDTO loginHandler(MemberDTO memberDTO);

    MemberDTO signHandler(MemberDTO memberDTO);

    int isExistKTag(String kTag);

    int isExistToken(String token);

    int isExistId(String memId);

    MemberDTO findIdHandler(MemberDTO memberDTO);

    String resetPwHandler(MemberDTO memberDTO);

    List<MemberDTO> getAllMember();

}
