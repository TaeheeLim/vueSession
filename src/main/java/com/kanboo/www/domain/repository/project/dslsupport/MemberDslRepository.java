package com.kanboo.www.domain.repository.project.dslsupport;


import com.kanboo.www.domain.entity.member.Member;

public interface MemberDslRepository {
	Member findByMemberIdx(Long memberIdx);
}
