package com.genrichers.WFH_app.Repositorys;

import com.genrichers.WFH_app.Entity.EmployeeEntity;
import com.genrichers.WFH_app.Entity.WfhRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WfhRequestRepo extends JpaRepository<WfhRequestEntity,Integer> {

    @Query("SELECT e from WfhRequestEntity e where e.emp_id=:emp_id")
     List<WfhRequestEntity>getWfhRequests(@Param("emp_id") EmployeeEntity emp_id);

    @Query("SELECT e FROM WfhRequestEntity e WHERE e.mng_id=:mng_id")
    List<WfhRequestEntity>getAllWfhRequestsForManager(@Param("mng_id") Integer mng_id);


//    @Query("SELECT u FROM WfhRequestEntity u WHERE u.status LIKE %:status%")
//    List<WfhRequestEntity>getAllWfhRequestsForManager(@Param("status") String status);

    @Query("SELECT w FROM WfhRequestEntity w WHERE w.wfh_date BETWEEN :start_date AND :end_date")
    List<WfhRequestEntity> findByWfhDateBetween(@Param("start_date") LocalDate startDate,
                                          @Param("end_date") LocalDate end_date);

}
