package org.techhub.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
 private int projectId;
 private String projectName;
 private String Desc;
 private Date start_date;
 private Date end_date;
 
}
