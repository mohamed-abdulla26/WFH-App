package com.genrichers.WFH_app.Beans;

import com.genrichers.WFH_app.Entity.WfhRequestEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WfhResultBean {
    private int status;
    private String message;
    private WfhRequestEntity WfhRequest;
}
