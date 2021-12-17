package com.kanboo.www.domain.repository.project;

import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.repository.project.dslsupport.CalendarDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>, CalendarDslRepository {
	Calendar findByCalIdx(Long calIdx);
}
