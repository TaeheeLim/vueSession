package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Issue;
import com.kanboo.www.domain.entity.project.QIssue;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class IssueDslRepositoryImpl implements IssueDslRepository {

	private final EntityManager em;

	@Override
	public List<Issue> findAllByProjectIdxDesc(Long projectIdx) {
		QIssue qIssue = QIssue.issue;
		JPAQuery<Issue> query = new JPAQuery<>(em);
		return query.from(qIssue)
				.where(qIssue.project.prjctIdx.eq(projectIdx))
				.orderBy(qIssue.issueDate.desc())
				.fetch();
	}
}
