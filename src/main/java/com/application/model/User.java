package com.application.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.web.servlet.View;

import java.util.Date;

@Data
public class User {
    @JsonView(View.class)
    private String id;
    @JsonView(View.class)
    private String userId;
    @JsonView(View.class)
    private String fullName;
    @JsonView(View.class)
    private String password;
    @JsonView(View.class)
    private String userSection;
    @JsonView(View.class)
    private String email;
    @JsonView(View.class)
    private String extNo;
    @JsonView(View.class)
    private String userRole;
    @JsonView(View.class)
    private String authorities;
    @JsonView(View.class)
    private String createdBy;
    @JsonView(View.class)
    private Date createdDt;
    @JsonView(View.class)
    private String modifiedBy;
    @JsonView(View.class)
    private Date modifiedDt;
}
