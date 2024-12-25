package org.techhub.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
  
	private int taskId;
	private String taskName;
	private Date taskDate;
	private String status;
	private String taskType;
	private String priority;
	private Date deadLine;
	
	
}
