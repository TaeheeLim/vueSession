package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.member.QMember;
import com.kanboo.www.domain.entity.member.QProjectMember;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.entity.project.QCalendar;
import com.kanboo.www.domain.entity.project.QIssue;
import com.kanboo.www.domain.entity.project.QProject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ProjectsDslRepositoryImpl implements ProjectsDslRepository {

    private final EntityManager em;

    @Override
    public List<Project> getIssueAndCalendar(String memTag) {

        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QIssue issue = QIssue.issue;
        QCalendar calendar = QCalendar.calendar;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.select(project)
                .from(project)
                .fetch();
    }
}
