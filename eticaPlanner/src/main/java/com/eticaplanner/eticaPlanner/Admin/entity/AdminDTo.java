package com.eticaplanner.eticaPlanner.Admin.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AdminDTo {

    private String adminId;
    private String adminPw;
    private String adminName;
    private String adminPhone;
    private String adminEmail;
}
