package com.aquarium.controller;

import com.aquarium.pojo.SysStaff;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysStaffService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/staff")
public class SysStaffController {

    @Resource
    private ISysStaffService staffService;

    /**
     * 更新或新增人员
     *
     * @param sysStaff
     * @return
     */
    @PostMapping("/addOrUpdateStaff")
    public ResponseVo addOrUpdateStaff(@RequestBody SysStaff sysStaff) {
        return staffService.createOrUpdate(sysStaff);
    }

    @PostMapping("/delete")
    public ResponseVo deleteStaff(int staffId) {
        if (staffService.removeById(staffId)) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }
}
