package com.aquarium.controller;

import com.aquarium.dto.UpdateManagedVenueDTO;
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
     * 新增或更新人员信息
     *
     * @param sysStaff
     * @return
     */
    @PostMapping("/addOrCreate")
    public ResponseVo addOrCreate(@RequestBody SysStaff sysStaff) {
        if (staffService.saveOrUpdate(sysStaff)) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
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
     * 查找人员管理的实验室
     *
     * @param staffId
     * @return
     */
    @PostMapping("/managedVenue")
    public ResponseVo findManagedVenue(int staffId) {
        return staffService.findManagedVenue(staffId);
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
            @RequestParam(name = "pageNo") long page,
            @RequestParam(name = "pageSize") long limit,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sort) {
        return staffService.listStaff(page, limit, name);
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

    /**
     * 更新管理的场馆
     *
     * @param newVenueDTO
     * @return
     */
    @PostMapping("/updateManagedVenue")
    public ResponseVo updateManagedVenue(@RequestBody UpdateManagedVenueDTO newVenueDTO) {
        return staffService.updateManagedVenue(newVenueDTO);
    }
}
