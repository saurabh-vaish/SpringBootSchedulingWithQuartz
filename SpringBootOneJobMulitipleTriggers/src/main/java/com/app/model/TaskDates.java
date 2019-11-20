package com.app.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDates{
  
    @NotEmpty
    private String body;
    
    private String taskDate;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private ZoneId timeZone;

  
}
