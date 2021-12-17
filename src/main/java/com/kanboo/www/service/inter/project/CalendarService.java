package com.kanboo.www.service.inter.project;

import com.kanboo.www.dto.project.CalendarDTO;

import java.util.List;

public interface CalendarService {
	List<CalendarDTO> calendarHandler(CalendarDTO calendarDTO);

	CalendarDTO updateCalendar(CalendarDTO calendarDTO);
}
