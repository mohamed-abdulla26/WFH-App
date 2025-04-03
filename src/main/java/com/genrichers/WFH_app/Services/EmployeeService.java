package com.genrichers.WFH_app.Services;

import com.genrichers.WFH_app.Entity.EmployeeEntity;
import com.genrichers.WFH_app.Exception.ResourceNotFoundException;
import com.genrichers.WFH_app.Repositorys.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    //get all employee
    public List<EmployeeEntity> getAllEmployees(){
        return employeeRepo.findAll();
    }

    //get single employee
    public EmployeeEntity getSingleEmployee(Integer id){
        return employeeRepo.findById(id).orElseThrow(()-> ResourceNotFoundException.builder().message("user doesn't exist with this id:"+id).build());
    }



    //create employee
    public EmployeeEntity createEmployee(EmployeeEntity emp){
        return employeeRepo.save(emp);
    }

    //update employee
    public EmployeeEntity updateEmployee(EmployeeEntity emp,Integer id){
        EmployeeEntity employeedata=employeeRepo.findById(id).orElseThrow(()-> ResourceNotFoundException.builder().message("user doesn't exist with this id:"+id).build());
        employeedata.setMail(emp.getMail());
        employeedata.setName(emp.getName());
        employeedata.setRole(emp.getRole());
        return employeeRepo.save(employeedata);
    }
   //delete single data
    public EmployeeEntity deleteEmployee(Integer id){
        EmployeeEntity result=employeeRepo.findById(id).orElseThrow(()-> ResourceNotFoundException.builder().message("user doesn't exist with this id:"+id).build());
        employeeRepo.delete(result);
        return result;
    }

    //delete all data
    public List<EmployeeEntity> deleteAllEmployee(){
        List<EmployeeEntity> result=employeeRepo.findAll();
        employeeRepo.deleteAll(result);
        return result;
    }
}
