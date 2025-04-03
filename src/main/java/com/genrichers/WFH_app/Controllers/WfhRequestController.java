package com.genrichers.WFH_app.Controllers;

import com.genrichers.WFH_app.Beans.AllWfhResultBean;
import com.genrichers.WFH_app.Beans.WfhResultBean;
import com.genrichers.WFH_app.Entity.EmployeeEntity;
import com.genrichers.WFH_app.Entity.WfhRequestEntity;
import com.genrichers.WFH_app.Repositorys.WfhRequestRepo;
import com.genrichers.WFH_app.Services.EmployeeService;
import com.genrichers.WFH_app.Services.WfhRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
public class WfhRequestController {
    @Autowired
    private WfhRequestService WfhRequestService;
    @Autowired
    private EmployeeService EmployeeService;
    @Autowired
    private WfhRequestRepo WfhRequestRepo;

    //employee
    //create wfh request

    @PostMapping("/api/employee/wfh_request/{id}")
    public ResponseEntity<WfhResultBean> createWfhRequest(@RequestBody WfhRequestEntity request, @PathVariable Integer id){

        //checking that the given id is existing user's id
       EmployeeEntity EmployeeData=EmployeeService.getSingleEmployee(id);
       if(EmployeeData.getEmp_id().equals(id)&&id!=1){

 //checking the given date that's not in past
           LocalDate today=LocalDate.now();
           if(request.getWfh_date().isBefore(today)){
               WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("given invalid date").build();
               return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
           }else{


//           checking the date whether that's already exist
               List<WfhRequestEntity>data=WfhRequestService.getWfhRequests(EmployeeData);
//
               boolean exists = data.stream().anyMatch(n -> n.getWfh_date().equals(request.getWfh_date()));
//
               if(exists){
                   WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("request is already exist on this date:").build();
                   return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
               }
               else {

                   //current date not allowed
                   if(request.getWfh_date().equals(today)){
                       WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("current date not allowed").build();
                       return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
                   }
                   else{
                       request.setEmp_id(EmployeeData);
//                   request.setManager(EmployeeData);
                       request.setMng_id(EmployeeData.getMng_id());
                       request.setName(EmployeeData.getName());
                       request.setMail(EmployeeData.getMail());
                       request.setRole(EmployeeData.getRole());
                       request.setStatus("pending");
                       WfhRequestEntity result=WfhRequestService.createWfhRequest(request);
                       WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.OK.value()).WfhRequest(result).message("request submitted").build();
                       return new ResponseEntity<>(bean,HttpStatus.OK);
                   }

               }
           }



       }else {
            WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("admin's id").build();
           return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
       }

    }



    //get wfh requests

    @GetMapping("/api/employee/get_wfh_request/{id}")
    public ResponseEntity<AllWfhResultBean>getWfhRequests(@PathVariable Integer id){
        EmployeeEntity EmploeeData=EmployeeService.getSingleEmployee(id);
        if(EmploeeData.getRole().equals("admin")){

            AllWfhResultBean bean= AllWfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("given admin id:"+id).build();
            return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);

        }else{

            List<WfhRequestEntity>result=WfhRequestService.getWfhRequests(EmploeeData);
            if(!result.isEmpty()){
                AllWfhResultBean bean= AllWfhResultBean.builder().requests(result).status(HttpStatus.OK.value()).message("got all data").count(result.size()).build();
                return new ResponseEntity<>(bean,HttpStatus.OK);
            }else {
                AllWfhResultBean bean= AllWfhResultBean.builder().requests(result).status(HttpStatus.NOT_FOUND.value()).message("request doesn't exist with this id:"+id).count(result.size()).build();
                return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
            }
        }

    }



    //get all employee request(for admin only)

    @GetMapping(value = "/api/admin/get_all_request/{id}")
    public ResponseEntity<AllWfhResultBean>getAllWfhRequest(@PathVariable Integer id){
        EmployeeEntity empData=EmployeeService.getSingleEmployee(id);
        if(empData.getRole().equals("admin")){
            List<WfhRequestEntity>result=WfhRequestService.getAllWfhRequest();
            AllWfhResultBean bean= AllWfhResultBean.builder().requests(result).status(HttpStatus.OK.value()).message("got all request").count(result.size()).build();
            return new ResponseEntity<>(bean,HttpStatus.OK);
        }else {

            AllWfhResultBean bean= AllWfhResultBean.builder().status(HttpStatus.NO_CONTENT.value()).message("admin  only can access").build();
            return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);


        }

    }

    //get all  request (for manager only)
    @GetMapping(value = "/api/manager/get_all_request/{mng_id}")
    public ResponseEntity<AllWfhResultBean>getAllWfhResult(@PathVariable Integer mng_id){
        EmployeeEntity empData=EmployeeService.getSingleEmployee(mng_id);

        if(empData.getRole().equals("manager")){
            List<WfhRequestEntity>result=WfhRequestService.getAllWfhRequestsForManager(empData.getEmp_id());
            if(!result.isEmpty()){
                AllWfhResultBean bean= AllWfhResultBean.builder().requests(result).status(HttpStatus.OK.value()).count(result.size()).message("got all data").build();
                return new ResponseEntity<>(bean,HttpStatus.OK);
            }else{
                AllWfhResultBean bean= AllWfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("no data found").build();
                return new ResponseEntity<>(bean,HttpStatus.OK);
            }

        }else {

            AllWfhResultBean bean= AllWfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("manager only can Access").build();
            return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);


        }

    }


    //get all pending  request (for manager only)
    @GetMapping(value = "/api/manager/get_all_status_request/{mng_id}/{status}")
    public ResponseEntity<AllWfhResultBean>getAllPendingWfhResult(@PathVariable Integer mng_id,@PathVariable String status){
        EmployeeEntity empData=EmployeeService.getSingleEmployee(mng_id);

        if(empData.getRole().equals("manager")||empData.getRole().equals("admin")){
            List<WfhRequestEntity>result=WfhRequestService.getAllWfhRequestsForManager(empData.getEmp_id());
            if(!result.isEmpty()){
                List<WfhRequestEntity>resultStream=result.stream().filter(n->n.getStatus().equals(status)).toList();
                if(!resultStream.isEmpty()){
                    AllWfhResultBean bean= AllWfhResultBean.builder().requests(resultStream).status(HttpStatus.OK.value()).count(resultStream.size()).message("got all data").build();
                    return new ResponseEntity<>(bean,HttpStatus.OK);
                }
                else {
                    AllWfhResultBean bean= AllWfhResultBean.builder().requests(resultStream).status(HttpStatus.NOT_FOUND.value()).message("no data found with:"+status).build();
                    return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
                }

            }else{
                AllWfhResultBean bean= AllWfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("no data found").build();
                return new ResponseEntity<>(bean,HttpStatus.OK);
            }

        }else {

            AllWfhResultBean bean= AllWfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("manager or admin only can Access").build();
            return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);


        }

    }






    //request approve or reject api for manager only

    @PutMapping(value="/api/manager/response/{mng_id}/{wfh_id}")
    public ResponseEntity<WfhResultBean>approveWfhRequest(@RequestBody WfhRequestEntity request,@PathVariable Integer wfh_id,@PathVariable Integer mng_id){
        EmployeeEntity empData=EmployeeService.getSingleEmployee(mng_id);

        //extra feature added that admin also can approve or reject the request
        if(empData.getRole().equals("manager")||empData.getRole().equals("admin")){
            WfhRequestEntity result=WfhRequestService.approveWfhRequest(wfh_id);
            if(result.getMng_id().equals(mng_id)||mng_id==1){
                result.setStatus(request.getStatus());
                WfhRequestRepo.save(result);
                WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.OK.value()).WfhRequest(result).message("status updated successfully").build();
                return new ResponseEntity<>(bean,HttpStatus.OK);
            }else {
                WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("related manager only can approve or reject").build();
                return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
            }

        }else{
            WfhResultBean bean= WfhResultBean.builder().status(HttpStatus.NOT_FOUND.value()).message("manager or admin only can Access").build();
            return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
        }

    }

//   //get all requests using filter
    @GetMapping(value="/api/admin/get_employee/{id}")
    public ResponseEntity<AllWfhResultBean>findByWfhDateBetween(@PathVariable Integer id,@RequestParam LocalDate start_date,@RequestParam LocalDate end_date){

        EmployeeEntity empData=EmployeeService.getSingleEmployee(id);

            if(empData.getRole().equals("admin")){
                if (start_date.isAfter(end_date)) {
                    AllWfhResultBean bean= AllWfhResultBean.builder().message("Start date must be before end date.").status(HttpStatus.NOT_FOUND.value()).build();
                    return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
                }
                else{
                    List<WfhRequestEntity>result=WfhRequestService.findByWfhDateBetween(start_date,end_date);
                    if(result.isEmpty()){
                        AllWfhResultBean bean= AllWfhResultBean.builder().message("no data found on this period").status(HttpStatus.NOT_FOUND.value()).build();
                        return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
                    }else{
                        AllWfhResultBean bean= AllWfhResultBean.builder().requests(result).count(result.size()).message("got all data").status(HttpStatus.OK.value()).build();
                        return new ResponseEntity<>(bean,HttpStatus.OK);
                    }
                }
            }else{
                AllWfhResultBean bean= AllWfhResultBean.builder().message("admin only can access").status(HttpStatus.NOT_FOUND.value()).build();
                return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);






            }

    }

}
