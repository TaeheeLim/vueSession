package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.entity.project.QCalendar;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class CalendarDslRepositoryImpl implements CalendarDslRepository{

	private final EntityManager em;

	@Override
	public List<Calendar> findByProjectIdxAndMemberIdx(Long projectIdx, Long memIdx) {
		QCalendar qCalendar = QCalendar.calendar;
		JPAQuery<Calendar> query = new JPAQuery<>(em);
		return query.from(qCalendar)
				.where(qCalendar.project.prjctIdx.eq(projectIdx)
						, qCalendar.member.memIdx.eq(memIdx))
				.fetch();
	}
}
