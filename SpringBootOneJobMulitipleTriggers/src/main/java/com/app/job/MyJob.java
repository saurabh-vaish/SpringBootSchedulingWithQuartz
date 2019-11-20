package com.app.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class MyJob extends QuartzJobBean {

	public static Integer count=0;
	
	
	//  method to execute job 
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        
        
        System.out.println("Job started :"+(++count)+" at: "+new Date());
        
    }

}
