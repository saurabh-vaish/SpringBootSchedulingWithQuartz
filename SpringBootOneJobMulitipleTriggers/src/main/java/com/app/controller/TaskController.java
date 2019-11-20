package com.app.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.job.ScheduleUtil;
import com.app.model.TaskDates;

@Controller
@RequestMapping("/")
public class TaskController {

	
	@Autowired
	private ScheduleUtil scheduleUtil;
	
	
	@GetMapping("/")
	public String getIndex(Model map)
	{
		map.addAttribute("task",new TaskDates());
		
		return "index";
	}
	
	
	
	@PostMapping("/save")
	public String saveTaskDate(@ModelAttribute TaskDates taskDate,Model map) throws ParseException
	{
		System.out.println(taskDate);

		String str = taskDate.getTaskDate();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		
		taskDate.setDateTime(dateTime);
		
		taskDate.setTimeZone(ZoneId.of("Asia/Kolkata"));
		
		scheduleUtil.scheduleJob(taskDate);
		
		map.addAttribute("msg","seccess");
		
		return "success";
	}
	
}
