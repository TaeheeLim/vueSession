package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.member.idclass.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
}
