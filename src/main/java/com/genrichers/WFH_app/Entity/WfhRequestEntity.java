package com.genrichers.WFH_app.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Wfh_Request")
@Data
@Getter
@Setter
public class WfhRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer req_id;
    @CreationTimestamp
    private LocalDate applied_date;
    private LocalDate wfh_date;
    private String reason;
    private String status;
    @ManyToOne(targetEntity = EmployeeEntity.class)
    @JoinColumn(name = "emp_id",referencedColumnName = "emp_id")
    private EmployeeEntity emp_id;

//    @ManyToOne
//    @JoinColumn(name = "mng_id", referencedColumnName = "mng_id")
//    private EmployeeEntity mng_id;

//    @ManyToOne
//    @JoinColumn(name = "mng_id", referencedColumnName = "emp_id")
//    private EmployeeEntity manager;
    private Integer mng_id;
    private String name;
    private String mail;
    private String role;
}
