package com.aquarium.controller;

import com.aquarium.pojo.SysStaff;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysStaffService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 删除
     *
     * @param staffId
     * @return
     */
    @PostMapping("/delete")
    public ResponseVo deleteStaff(int staffId) {
        if (staffService.removeById(staffId)) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

    /**
     * 人员分页查询
     *
     * @param page
     * @param limit
     * @param name
     * @param sort
     * @return
     */
    @GetMapping("/list")
    public ResponseVo list(
            long page,
            long limit,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sort) {
        return staffService.listPerson(page, limit, name);
    }

    /**
     * 人员查询不分页
     *
     * @return
     */
    @GetMapping("/originList")
    public ResponseVo originList() {
        List<SysStaff> list = staffService.list();
        return ResponseVo.success().data("items", list).data("total", list.size());
    }
}
