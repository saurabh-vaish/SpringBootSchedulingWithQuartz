package com.app.job;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.model.TaskDates;


@Component
public class ScheduleUtil {

	public static Integer count=0;
	
	@Autowired(required = true)
	private Scheduler scheduler;					// auto configured by spring boot
	
	
	public String scheduleJob(TaskDates taskDate) {
		
		try {
            ZonedDateTime dateTime = ZonedDateTime.of(taskDate.getDateTime(), taskDate.getTimeZone());
            
            if(dateTime.isBefore(ZonedDateTime.now())) {
               return "Date is Before";
            }

            JobDetail jobDetail = buildJobDetail(taskDate);				// building job
            
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);		// trigger time to execute
            
            scheduler.scheduleJob(jobDetail, trigger);						// schedule job

           return "success";
        } 
		catch (SchedulerException ex) {
           
			return "fail";
			
        }
		
	}

	
	// building job -- JobDetail defines a particular job
	 private JobDetail buildJobDetail(TaskDates taskDate) {
		 
	        JobDataMap jobDataMap = new JobDataMap();					// stores information for job

	        taskDate.setBody("task executed :"+ (++count));			//
	        
	        jobDataMap.put("body", taskDate.getBody());

	        return JobBuilder.newJob(MyJob.class)
	                .withIdentity(UUID.randomUUID().toString(), "my-jobs")			// setting job identity
	                .withDescription("Execute My Custom Scheduled Job")
	                .usingJobData(jobDataMap)
	                .storeDurably()
	                .build();
	    }

	 	// building trigger for job
	 
	    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
	    	
	        return TriggerBuilder.newTrigger()
	                .forJob(jobDetail)
	                .withIdentity(jobDetail.getKey().getName(), "myjob-triggers")
	                .withDescription("My Custom Scheduled Trigger")
	                .startAt(Date.from(startAt.toInstant()))
	                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
	                .build();
	    }
	}
