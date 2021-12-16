package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Issue;
import com.kanboo.www.domain.repository.project.dslsupport.IssueDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>, IssueDslRepository {

}
