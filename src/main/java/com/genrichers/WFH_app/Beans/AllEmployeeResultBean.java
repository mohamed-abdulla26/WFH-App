package com.genrichers.WFH_app.Beans;

import com.genrichers.WFH_app.Entity.EmployeeEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AllEmployeeResultBean {
    private int status;
    private String message;
    private List<EmployeeEntity>employees;
    private int count;

}
