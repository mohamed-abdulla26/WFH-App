package com.genrichers.WFH_app.Controllers;

import com.genrichers.WFH_app.Beans.AllEmployeeResultBean;
import com.genrichers.WFH_app.Beans.EmployeeResultBean;
import com.genrichers.WFH_app.Entity.EmployeeEntity;
import com.genrichers.WFH_app.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //get all employee
    @GetMapping(value = "/api/employee/getall")
    public  ResponseEntity<AllEmployeeResultBean> getAllEmployee(){
        List<EmployeeEntity> result=employeeService.getAllEmployees();
        if(!result.isEmpty()){
            AllEmployeeResultBean bean= AllEmployeeResultBean.builder().employees(result).status(HttpStatus.OK.value()).count(result.size()).message("got all data").build();
            return new ResponseEntity<>(bean,HttpStatus.OK);
        }
        else {
            AllEmployeeResultBean bean= AllEmployeeResultBean.builder().employees(result).status(HttpStatus.NOT_FOUND.value()).count(result.size()).message("no data exist").build();
            return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
        }

    }


    //get single employee
    @GetMapping(value = "/api/employee/{id}")
    public ResponseEntity<EmployeeResultBean> getSingleEmployee(@PathVariable Integer id){
    EmployeeEntity result=employeeService.getSingleEmployee(id);
    EmployeeResultBean bean= EmployeeResultBean.builder().employee(result).status(HttpStatus.OK.value()).message("got single data").build();
    return new ResponseEntity<>(bean,HttpStatus.OK);
    }


    //creating employee
    @PostMapping(value = "/api/employee/create")

    public ResponseEntity<EmployeeResultBean> createEmployee(@RequestBody EmployeeEntity emp){
        EmployeeEntity result=employeeService.createEmployee(emp);
        EmployeeResultBean bean= EmployeeResultBean.builder().employee(result).status(HttpStatus.OK.value()).message("employee added successfully").build();
        return new ResponseEntity<>(bean,HttpStatus.OK);
    }

    //updating employee
    @PutMapping(value = "/api/employee/update/{id}")
    public ResponseEntity<EmployeeResultBean> updateEmployee(@RequestBody EmployeeEntity emp,@PathVariable Integer id){
        EmployeeEntity result=employeeService.updateEmployee(emp,id);
        EmployeeResultBean bean= EmployeeResultBean.builder().employee(result).status(HttpStatus.OK.value()).message("data updated successfully").build();
        return new ResponseEntity<>(bean,HttpStatus.OK);
    }

    //delete single employee
    @DeleteMapping(value = "api/employee/delete/{id}")
    public ResponseEntity<EmployeeResultBean> deleteEmployee(@PathVariable Integer id){
     EmployeeEntity result=employeeService.deleteEmployee(id);
     EmployeeResultBean bean= EmployeeResultBean.builder().employee(result).status(HttpStatus.OK.value()).message("data deleted with this id:"+id).build();
     return new ResponseEntity<>(bean,HttpStatus.OK);
    }

    //delete all data
    @DeleteMapping("/api/employee/deleteall")
    public ResponseEntity<AllEmployeeResultBean> deleteAllEmployee(){
        List<EmployeeEntity> result=employeeService.deleteAllEmployee();
        if(!result.isEmpty()){
            AllEmployeeResultBean bean=AllEmployeeResultBean.builder().employees(result).status(HttpStatus.OK.value()).count(result.size()).message("all data deleted").build();
            return new ResponseEntity<>(bean,HttpStatus.OK);
        }else{
            AllEmployeeResultBean bean=AllEmployeeResultBean.builder().employees(result).status(HttpStatus.NOT_FOUND.value()).count(result.size()).message("already empty").build();
            return new ResponseEntity<>(bean,HttpStatus.NOT_FOUND);
        }


    }
}
