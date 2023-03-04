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

    /**
     * 登录
     *
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public ResponseVo login(@RequestBody SysAdministrator admin) {
        return administratorService.login(admin);
    }

    /**
     * 登出
     *
     * @return
     */
    @PostMapping("/logout")
    public ResponseVo logout() {
        return ResponseVo.success();
    }
}
