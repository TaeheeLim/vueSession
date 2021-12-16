package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.CalendarDTO;
import com.kanboo.www.service.inter.project.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scheduler")
public class CalendarController {

	private final CalendarService calendarService;

	@GetMapping(value = "/getAllSchedules")
	public CalendarDTO CalendarHandler(CalendarDTO calendarDTO){
		System.out.println( "calendarDTO => " + calendarDTO);
		return null;
	}
}
