package com.application.operationlog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operationLog")
public class OperationLogController {
    @GetMapping("")
    public String getSomthing(){
        return "";
    }
    @GetMapping("/statusCombo")
    public String getStatusCombo(){
        return "";
    }
}
