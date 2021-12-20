package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsDslRepository {
    List<Project> getIssueAndCalendar(String memTag);
}
