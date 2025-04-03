package com.genrichers.WFH_app.Beans;

import com.genrichers.WFH_app.Entity.EmployeeEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResultBean {
    private int status;
    private String message;
    private EmployeeEntity employee;
}
