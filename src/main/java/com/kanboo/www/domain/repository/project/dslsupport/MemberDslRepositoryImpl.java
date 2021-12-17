package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.global.QRole;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class MemberDslRepositoryImpl implements MemberDslRepository{

	private final EntityManager em;

	@Override
	public Member findByMemberIdx(Long memberIdx) {
		QMember qMember = QMember.member;
		QRole qRole = QRole.role;

		JPAQueryFactory query = new JPAQueryFactory(em);

		return (Member) query.select(qMember)
				.from(qMember)
				.innerJoin(qMember.role, qRole)
				.fetchJoin()
				.where(qMember.memIdx.eq(memberIdx))
				.fetch();
	}

}
