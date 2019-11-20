package com.app.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class MyJob extends QuartzJobBean {

  
	//  method to execute job 
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();  // getting job info from JobDataMap
        
        String body = jobDataMap.getString("body");
        
        // my custom job work here to execute
        
        System.out.println(body);
        
    }

}
