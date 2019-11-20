package com.app.job;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
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

	
	@Autowired(required = true)
	private Scheduler scheduler;					// auto configured by spring boot
	
	private  static JobDetail jobDetail; // for job detail
	
	private Trigger trigger;  // for triggers
	
	
	
	public String scheduleJob(TaskDates taskDate) {
		
		try {
            ZonedDateTime dateTime = ZonedDateTime.of(taskDate.getDateTime(), taskDate.getTimeZone());
            
            
            if(dateTime.isBefore(ZonedDateTime.now())) { 	// given date is before not valid
               return "Date is Before";
            }
            
                        
            if(!scheduler.checkExists(new JobKey("My-Job")))			// checks particular job is already existed or not , if not schedule it
            {
            	jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("My-Job").withDescription("My same job for all triggers").storeDurably().build();
            	
            	trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(UUID.randomUUID().toString().substring(3,9))
            				.startAt(Date.from(dateTime.toInstant()))
            				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionIgnoreMisfires()).build();
            	
            	 scheduler.scheduleJob(jobDetail, trigger);	
            	 
            	 System.out.println("first time");
            	 
            }
            else {				// once job started only trigger will be add to job 
            	
            	 trigger = TriggerBuilder.newTrigger().forJob(jobDetail)				// for same job
            			 .withIdentity(UUID.randomUUID().toString().substring(3,9))
         				.startAt(Date.from(dateTime.toInstant()))
         				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionIgnoreMisfires()).build();
         	
            	 System.out.println("add triggers");

            	 scheduler.scheduleJob(trigger);						// schedule job
            	 
            }

            System.out.println("success");

           return "success";
        } 
		catch (SchedulerException ex) {
           
			return "fail";
			
        }
		
	}

}
