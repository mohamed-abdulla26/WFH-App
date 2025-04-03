package com.genrichers.WFH_app.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Data
@Setter
@Getter
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer emp_id;
    private Integer mng_id;
    private String name;
    private String mail;
    private String role;
}
