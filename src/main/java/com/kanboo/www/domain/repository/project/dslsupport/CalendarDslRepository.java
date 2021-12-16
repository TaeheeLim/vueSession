package com.kanboo.www.domain.repository.project.dslsupport;

import com.kanboo.www.domain.entity.project.Calendar;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarDslRepository {
	List<Calendar> findByProjectIdxAndMemberIdx(Long projectIdx, Long memIdx);
}
