package com.application.authen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
public class Login {
    private String username;
    private String password;
}
