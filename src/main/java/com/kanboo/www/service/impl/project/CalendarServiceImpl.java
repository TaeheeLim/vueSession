package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.domain.entity.project.Calendar;
import com.kanboo.www.domain.entity.project.Project;
import com.kanboo.www.domain.repository.member.MemberRepository;
import com.kanboo.www.domain.repository.project.CalendarRepository;
import com.kanboo.www.domain.repository.project.ProjectRepository;
import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.service.inter.project.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

	private final CalendarRepository calendarRepository;
	private final ProjectRepository projectRepository;
	private final MemberRepository memberRepository;
	
	@Override
	public CalendarDTO CalendarHandler(CalendarDTO calendarDTO) {
		List<Calendar> calendarList = calendarRepository
				.findByProjectIdxAndMemberIdx(calendarDTO.getProject().getPrjctIdx()
						, calendarDTO.getMember().getMemIdx());

		return null;
	}
}
