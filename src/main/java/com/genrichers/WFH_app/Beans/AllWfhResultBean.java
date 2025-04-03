package com.genrichers.WFH_app.Beans;

import com.genrichers.WFH_app.Entity.WfhRequestEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AllWfhResultBean {
    private int status;
    private String message;
    private List<WfhRequestEntity> requests;
    private int count;
}
