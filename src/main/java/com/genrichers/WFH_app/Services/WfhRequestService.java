package com.genrichers.WFH_app.Services;

import com.genrichers.WFH_app.Entity.EmployeeEntity;
import com.genrichers.WFH_app.Entity.WfhRequestEntity;
import com.genrichers.WFH_app.Exception.ResourceNotFoundException;
import com.genrichers.WFH_app.Repositorys.WfhRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WfhRequestService {
    @Autowired
    private WfhRequestRepo WfhRequestrepo;

    //wfh apply request for employee
    public WfhRequestEntity createWfhRequest(WfhRequestEntity request){
        return WfhRequestrepo.save(request);
    }
    //get single employee requests
    public List<WfhRequestEntity>getWfhRequests(EmployeeEntity id){
        return WfhRequestrepo.getWfhRequests(id);
    }

    //get all employee request for(Admin)
    public List<WfhRequestEntity>getAllWfhRequest(){
        return WfhRequestrepo.findAll();
    }

    //approve or reject wfh request api for manager only
    public WfhRequestEntity approveWfhRequest(Integer wfh_id){
        return WfhRequestrepo.findById(wfh_id).orElseThrow(()->new ResourceNotFoundException("data not found with this Req_id:"+wfh_id));
    }

    //get all request(for manager only)
    public List<WfhRequestEntity>getAllWfhRequestsForManager(Integer mng_id){
        return WfhRequestrepo.getAllWfhRequestsForManager(mng_id);
    }

    //get all employee by using date filter

    public List<WfhRequestEntity>findByWfhDateBetween(LocalDate start_date,LocalDate end_date){
        return WfhRequestrepo.findByWfhDateBetween(start_date,end_date);
    }
}
