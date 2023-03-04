package com.aquarium.controller;

import com.aquarium.pojo.SysAdministrator;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysAdministratorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class SysAdministratorController {

    @Resource
    private ISysAdministratorService administratorService;

    @GetMapping("/test")
    public ResponseVo test() {
        return ResponseVo.success();
    }

    @PostMapping("/login")
    public ResponseVo login(@RequestBody SysAdministrator admin) {
        return administratorService.login(admin);
    }
}
