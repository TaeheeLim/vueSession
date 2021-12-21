package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.board.QBoard;
import com.kanboo.www.domain.entity.board.QProjectBoard;
import com.kanboo.www.domain.entity.member.ProjectMember;
import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.member.QProjectMember;
import com.kanboo.www.domain.entity.project.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProjectMemberDslRepositoryImpl implements ProjectMemberDslRepository {

    private final EntityManager em;

    @Override
    public List<ProjectMember> getAllList(String memTag) {
        QMember member = QMember.member;
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QIssue issue = QIssue.issue;
        QCalendar calendar = QCalendar.calendar;

        JPAQueryFactory query = new JPAQueryFactory(em);


        return query
                .select(projectMember).distinct()
                .from(projectMember)
                .innerJoin(projectMember.member, member)
                .innerJoin(projectMember.project, project)
                .leftJoin(project.issueList, issue)
                .leftJoin(project.calendarList, calendar)
                .where(
                        projectMember.member.memTag.eq(memTag)
                )
                .fetch();
    }
}
