package com.genrichers.WFH_app.Repositorys;

import com.genrichers.WFH_app.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Integer> {
}
